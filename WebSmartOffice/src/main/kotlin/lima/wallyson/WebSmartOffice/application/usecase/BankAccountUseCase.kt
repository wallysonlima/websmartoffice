package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.repository.BankAccountRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
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
    private val bankAccountRepository: BankAccountRepository,
    private val personRepository: PersonRepository
) {
    // Conta Hardhat que recebe os fundos antes de zerar a conta do usuário
    private val hardhatPrivateKey = "0x4f3edf983ac636a65a842ce7c78d9aa706d3b113b37c84b63da4a06d6e3a10bc"
    private val hardhatCredentials = Credentials.create(hardhatPrivateKey)
    private val hardhatAddress = hardhatCredentials.address
    val defaultGasPrice:BigDecimal = (20000000000).toBigDecimal()

    /**
     * Obtém o saldo de uma conta Ethereum
     */
    fun getBalance(address: String): BigDecimal {
        val balanceWei = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
        return Convert.fromWei(balanceWei.toBigDecimal(), Convert.Unit.ETHER)
    }

    // Obtem o saldo em BRL da conta Ethereum
    fun getBalanceInBrl(ethAddress: String): BigDecimal {
        val amountInEth = getBalance(ethAddress)
        val ethPriceInBrl = getEthereumPrice()

        return amountInEth * ethPriceInBrl
    }

    fun getBalanceUser(cpf:String): Double {
        return bankAccountRepository.findBankAccountByBankCpf(cpf).balance.toDouble()
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
     * Deposita o valor em ETH na conta
     */
    fun depositToEthereumAccount(toAddress: String, amountInBrl: BigDecimal) {
        val ethPriceInBrl = getEthereumPrice()
        val amountInEth = (amountInBrl / ethPriceInBrl) - defaultGasPrice

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
            web3j, credentials, toAddress, amount - defaultGasPrice, Convert.Unit.ETHER
        ).send()

        return transactionReceipt.transactionHash
    }

    fun updateBalanceBankAccounts(
        cpfBuyer: String,
        cpfSeller: String,
        priceInBrl: BigDecimal
    ) {
        val buyerBank = bankAccountRepository.findBankAccountByBankCpf(cpfBuyer)
        val sellerBank = bankAccountRepository.findBankAccountByBankCpf(cpfSeller)

        if ( buyerBank.balance < priceInBrl ) {
            throw IllegalStateException("Saldo insuficiente para realizar a compra.")
        }

        println("saldo Comprador: " + buyerBank.balance.toDouble())
        println("saldo Vendedor: " + sellerBank.balance.toDouble())
        println("Preco Imovel em BRL: " + priceInBrl.toDouble())

        buyerBank.balance = buyerBank.balance - priceInBrl
        sellerBank.balance = sellerBank.balance + priceInBrl

        println("after -----------------")
        println("saldo Comprador: " + buyerBank.balance.toDouble())
        println("saldo Vendedor: " + sellerBank.balance.toDouble())
        println("Preco Imovel em BRL: " + priceInBrl)
        
        bankAccountRepository.save(buyerBank)
        bankAccountRepository.save(sellerBank)
    }
}