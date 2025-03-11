package lima.wallyson.WebSmartOffice.application.usecase

import blockchain_java.PropertySale
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.ContractsEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.ContractRepository
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import org.web3j.protocol.Web3j
import java.math.BigDecimal

@Service
class SmartContractUseCase(
    private val bankAccountUseCase: BankAccountUseCase,
    private val contractRepository: ContractRepository,
    private val web3j: Web3j
) {

    fun deployContract(
        cpfBuyer: String,
        cpfSeller: String,
        privateKeyFromBankAccount: String,
        address: String,
        propertySize: BigInteger,
        priceInBrl: BigDecimal,
        registerProperty: String,
        notarialDeed: String
    ): String {
        val ethPriceInWei = bankAccountUseCase.getEthereumPrice() // Convertendo para BigDecimal
        val priceInWei = (priceInBrl / ethPriceInWei).toBigInteger()  // Convertendo corretamente para WEI
        val credentials = Credentials.create(privateKeyFromBankAccount)

        val contract = PropertySale.deploy(
            web3j, credentials, DefaultGasProvider(),
            address, propertySize, priceInWei, registerProperty, notarialDeed
        ).send()

        contractRepository.save(
            ContractsEntity(
                cpfBuyer = cpfBuyer,
                cpfSeller = cpfSeller,
                registerProperty = registerProperty,
                contractAddress = contract.contractAddress
            )
        )

        return contract.contractAddress
    }
}


