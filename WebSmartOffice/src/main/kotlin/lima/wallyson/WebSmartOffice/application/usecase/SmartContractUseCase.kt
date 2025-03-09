package lima.wallyson.WebSmartOffice.application.usecase

import blockchain_java.PropertySale
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import org.web3j.protocol.Web3j
import java.math.BigDecimal

@Service
class SmartContractUseCase(
    private val bankAccountUseCase: BankAccountUseCase,
    private val web3j: Web3j
) {

    fun deployContract(
        contractAddress:String,
        propertyAddress: String,
        propertySize: BigInteger,
        priceInBrl: BigDecimal,
        registerProperty: String,
        notarialDeed: String
    ): String {
        val ethPriceInWei = bankAccountUseCase.getEthereumPrice() // Convertendo para BigDecimal
        val priceInWei = (priceInBrl / ethPriceInWei).toBigInteger()  // Convertendo corretamente para WEI
        val credentials = Credentials.create(contractAddress)

        val contract = PropertySale.deploy(
            web3j, credentials, DefaultGasProvider(),
            propertyAddress, propertySize, priceInWei, registerProperty, notarialDeed
        ).send()

        return contract.contractAddress
    }
}


