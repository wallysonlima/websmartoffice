package lima.wallyson.WebSmartOffice.application.usecase

import blockchain_java.PropertySale
import jakarta.transaction.Transactional
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.AddressEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PropertyEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.AddressRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.ContractRepository
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
    val contractRepository: ContractRepository,
    val web3j: Web3j
) {
    private val log: Logger = LoggerFactory.getLogger(PropertyUseCase::class.java)

    @Transactional
    fun register(request: PropertyRequestDTO): PropertyResponseDTO {
        log.info("c=RegisterPropertyUseCase, m=register, i=init")

        val person = personRepository.findByCpf(request.personCpf)

        if (person == null) throw IllegalArgumentException("Erro, Pessoa não encontrada!")

        try {
            propertyRepository.save(
                PropertyEntity(
                    propertyCpf = request.personCpf,
                    registerProperty = request.registerProperty,
                    notarialDeed = request.notarialDeed,
                    price = request.price,
                    size = request.size,
                )
            ).let {
                val address = AddressEntity(
                    propertyRegister = request.registerProperty,
                    streetName = request.address.streetName,
                    number = request.address.number,
                    complementAddress = request.address.complementAddress,
                    district = request.address.district,
                    city = request.address.city,
                    state = request.address.state,
                    postalCode = request.address.postalCode,
                )

                addressRepository.save(address)
            }

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
        registerProperty: String,
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

        //bankAccount.transferFundsBetweenEthereumAccounts(
        //    buyerPrivateKey,
        //    contractAddress,
        //    amountBrl
        //)

        // Chamar a função buyProperty() no contrato para confirmar a compra
        val transactionReceipt = contract.buyProperty(priceInWei).send().also {
            //Atualiza saldo contas comprador e vendedor
            bankAccount.updateBalanceBankAccounts(
                cpfBuyer,
                cpfSeller
            )
        }

        // Salva o hash da transacao no contrato
        saveContract(
            registerProperty,
            transactionReceipt.transactionHash
        )

        return "Compra realizada com sucesso! Hash da transação: ${transactionReceipt.transactionHash}"
    }

    fun getPropertiesFromUser(email:String): MutableList<PropertyResponseDTO> {
        val user = personRepository.findByEmail(email)
        var properties: MutableList<PropertyResponseDTO> = mutableListOf()
        propertyRepository.findAll().map { prop ->
           properties.add(PropertyResponseDTO(
               cpfProperty = prop.propertyCpf!!,
               registerProperty = prop.registerProperty,
               notarialDeed = prop.notarialDeed,
               price = prop.price,
               size = prop.size
           ))
        }

        return properties
    }

    private fun saveContract(
        registerProperty: String,
        transactionHash: String
    ) {
        val contractEntity = contractRepository.findByRegisterProperty(registerProperty)

        contractEntity.hashContractTransaction = transactionHash

        contractRepository.save(contractEntity)
    }
}




