package lima.wallyson.WebSmartOffice.application.usecase

import blockchain_java.PropertySale
import jakarta.transaction.Transactional
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.AddressEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PropertyEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.AddressRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PropertyRepository
import lima.wallyson.WebSmartOffice.web.dtos.PropertyRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PropertyResponseDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.tx.gas.DefaultGasProvider

@Service
class PropertyUseCase(
    val personRepository: PersonRepository,
    val propertyRepository: PropertyRepository,
    val addressRepository: AddressRepository,
    val bankAccount: BankAccountUseCase,
    val web3j: Web3j
) {
    private val log: Logger = LoggerFactory.getLogger(PropertyUseCase::class.java)

    @Transactional
    fun register(request: PropertyRequestDTO): PropertyResponseDTO {
        log.info("c=RegisterPropertyUseCase, m=register, i=init")

        val person = personRepository.findByCpf(request.personCpf)

        if (person == null) throw IllegalArgumentException("Erro, Pessoa não encontrada!")

        try {
            val savedProperty = propertyRepository.save(
                PropertyEntity(
                    registerProperty = request.registerProperty,
                    notarialDeed = request.notarialDeed,
                    price = request.price,
                    size = request.size,
                    owner = person
                )
            )

            val address = AddressEntity(
                streetName = request.address.streetName,
                number = request.address.number,
                complementAddress = request.address.complementAddress,
                district = request.address.district,
                city = request.address.city,
                state = request.address.state,
                postalCode = request.address.postalCode,
                property = savedProperty
            )

            addressRepository.save(address)

            return PropertyResponseDTO(
                registerProperty = request.registerProperty,
                notarialDeed = request.notarialDeed,
                price = request.price,
                size = request.size,
            )

            log.info("c=PropertyUseCase, m=register, i=end")
        } catch (ex: Exception) {
            log.info("c=PropertyUseCase, m=register, i=error, m=${ex.message}")
            throw ex
        }
    }

    fun buyProperty(
        cpfBuyer: String,
        cpfSeller: String,
        buyerPrivateKey: String,
        contractAddress: String
    ): String {
        val buyerCredentials = Credentials.create(buyerPrivateKey)

        // Carregar o contrato implantado
        val contract = PropertySale.load(contractAddress, web3j, buyerCredentials, DefaultGasProvider())

        // Verificar se a propriedade já foi vendida
        if (contract.isPropertySold.send()) {
            throw IllegalStateException("Esta propriedade já foi vendida.")
        }

        // Obter o preço do imóvel diretamente do contrato
        val priceInWei = contract.priceInWei.send()
        val amountBrl = bankAccount.convertFromEthToBrl(priceInWei.toBigDecimal())

        // Atualiza os valores das contas no banco de dados
        bankAccount.transferFundsBetweenBankAccounts(
            cpfBuyer,
            amountBrl,
            cpfSeller
        ).let {
            // Atualiza os valores das contas na rede ethereum
            bankAccount.transferFundsBetweenEthereumAccounts(
                buyerPrivateKey,
                contractAddress,
                amountBrl
            )
        }

        // Chamar a função buyProperty() no contrato para confirmar a compra
        val transactionReceipt = contract.buyProperty(priceInWei).send()

        return "Compra realizada com sucesso! Hash da transação: ${transactionReceipt.transactionHash}"
    }
}



