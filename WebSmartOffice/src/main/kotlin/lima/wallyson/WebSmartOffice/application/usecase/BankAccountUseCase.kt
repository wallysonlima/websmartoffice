package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.repository.BankAccountRepository
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL

@Service
class BankAccountUseCase(
    private val web3j: Web3j,
    private val bankAccountRepository: BankAccountRepository
) {
    // Conta Hardhat que recebe os fundos antes de zerar a conta do usuário
    private val hardhatPrivateKey = "0x4f3edf983ac636a65a842ce7c78d9aa706d3b113b37c84b63da4a06d6e3a10bc"
    private val hardhatCredentials = Credentials.create(hardhatPrivateKey)
    private val hardhatAddress = hardhatCredentials.address

    /**
     * Obtém o saldo de uma conta Ethereum
     */
    fun getBalance(address: String): BigDecimal {
        val balanceWei = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
        return Convert.fromWei(balanceWei.toBigDecimal(), Convert.Unit.ETHER)
    }

    /**
     * Transfere todo o saldo da conta Ethereum do usuário para a conta Hardhat (zerando a conta)
     */
    fun transferAllFunds(fromPrivateKey: String, fromAddress: String) {
        val credentials = Credentials.create(fromPrivateKey)
        val balanceEth = getBalance(fromAddress)

        if (balanceEth > BigDecimal.ZERO) {
            Transfer.sendFunds(
                web3j, credentials, hardhatAddress, balanceEth, Convert.Unit.ETHER
            ).send()
        }
    }

    /**
     * Obtém a cotação do Ethereum em BRL
     */
    fun getEthereumPrice(): BigDecimal {
        val url = URL("https://api.binance.com/api/v3/ticker/price?symbol=ETHBRL")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        return try {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(response)
            json.getString("price").toBigDecimal()
        } catch (e: Exception) {
            throw RuntimeException("Erro ao buscar cotação ETH: ${e.message}")
        }
    }

    /**
     * Converte BRL para ETH e deposita na conta Ethereum do usuário
     */
    fun depositToEthereumAccount(toAddress: String, amountInBrl: BigDecimal) {
        val ethPriceInBrl = getEthereumPrice()
        val amountInEth = amountInBrl / ethPriceInBrl

        Transfer.sendFunds(
            web3j, hardhatCredentials, toAddress, amountInEth, Convert.Unit.ETHER
        ).send()
    }

    /**
     * Transfere um valor de uma conta Ethereum para outra, convertendo BRL para ETH
     * @param fromPrivateKey Chave privada da conta de origem
     * @param toAddress Endereço da conta de destino
     * @param amount Valor em BRL a ser transferido
     * @return Hash da transação
     */
    fun transferFundsBetweenEthereumAccounts(
        fromPrivateKey: String,
        toAddress: String,
        amount: BigDecimal
    ): String {
        val credentials = Credentials.create(fromPrivateKey)

        val transactionReceipt: TransactionReceipt = Transfer.sendFunds(
            web3j, credentials, toAddress, amount, Convert.Unit.ETHER
        ).send()

        return transactionReceipt.transactionHash
    }

    fun transferFundsBetweenBankAccounts(
        cpfBuyer: String,
        amount: BigDecimal,
        cpfSeller: String
    ) {
        val buyerBank = bankAccountRepository.findBankAccountByCpf(cpfBuyer)
        val sellerBank = bankAccountRepository.findBankAccountByCpf(cpfSeller)

        if ( (buyerBank.balance - amount) > (0).toBigDecimal() ) {
            buyerBank.balance = buyerBank.balance - amount
            bankAccountRepository.save(buyerBank)

            sellerBank.balance = sellerBank.balance + amount
            bankAccountRepository.save(sellerBank)
        } else {
            throw IllegalStateException("Você tem saldo insuficiente para realizar a operação!.")
        }
    }

    fun convertFromEthToBrl(priceInWei: BigDecimal): BigDecimal {
        val ethAmount = Convert.fromWei(priceInWei, Convert.Unit.ETHER) // Wei → ETH
        val ethPriceInBrl = getEthereumPrice()
        return (ethAmount * ethPriceInBrl)
    }
}