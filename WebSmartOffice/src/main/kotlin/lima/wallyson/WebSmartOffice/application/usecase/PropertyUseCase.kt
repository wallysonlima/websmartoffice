package lima.wallyson.WebSmartOffice.application.usecase

import blockchain_java.PropertySale
import jakarta.transaction.Transactional
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PropertyEntity
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
import org.web3j.utils.Convert

@Service
class PropertyUseCase(
    val personRepository: PersonRepository,
    val propertyRepository: PropertyRepository,
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
                    address = request.address
                )
            )

            return PropertyResponseDTO(
                registerProperty = request.registerProperty,
                notarialDeed = request.notarialDeed,
                price = request.price,
                size = request.size,
                address = request.address
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

        // Calcular o preço em BRL com base na cotação do ETH
        val ethPriceInBrl = bankAccount.getEthereumPrice()

        println("ETH PRICE in BRL: " + ethPriceInBrl)
        val priceInEth = Convert.fromWei(priceInWei.toBigDecimal(), Convert.Unit.ETHER)
        val priceInBrl = priceInEth * ethPriceInBrl
        println("Price in BRL: " + priceInBrl)

        // Chamar a função buyProperty() no contrato para confirmar a compra
        val transactionReceipt = contract.buyProperty(priceInWei).send().also {
            //Atualiza saldo contas comprador e vendedor
            bankAccount.updateBalanceBankAccounts(
                cpfBuyer,
                cpfSeller,
                priceInBrl
            )
        }

        // Salva o hash da transacao no contrato
        saveContract(
            registerProperty,
            transactionReceipt.transactionHash,
            cpfBuyer,
            cpfSeller
        )

        //atualiza dono da propriedade
        val property = propertyRepository.findByPropertyCpfAndRegisterProperty(cpfSeller, registerProperty)
        property.propertyCpf = cpfBuyer
        propertyRepository.save(property)

        return "Compra realizada com sucesso! Hash da transação: ${transactionReceipt.transactionHash}"
    }

    fun getPropertiesFromUser(email:String): MutableList<PropertyResponseDTO> {
        val user = personRepository.findByEmail(email)
        var properties: MutableList<PropertyResponseDTO> = mutableListOf()
        propertyRepository.findAllByPropertyCpf(user.get().cpf).map { prop ->
           properties.add(PropertyResponseDTO(
               cpfProperty = prop.propertyCpf!!,
               registerProperty = prop.registerProperty,
               notarialDeed = prop.notarialDeed,
               price = prop.price,
               address = prop.address,
               size = prop.size
           ))
        }

        return properties
    }

    fun getProperties(): MutableList<PropertyResponseDTO> {
        var properties: MutableList<PropertyResponseDTO> = mutableListOf()
        propertyRepository.findAll().map { prop ->
            properties.add(PropertyResponseDTO(
                cpfProperty = prop.propertyCpf!!,
                registerProperty = prop.registerProperty,
                notarialDeed = prop.notarialDeed,
                price = prop.price,
                size = prop.size,
                address = prop.address
            ))
        }

        return properties
    }

    private fun saveContract(
        registerProperty: String,
        transactionHash: String,
        cpfBuyer: String,
        cpfSeller: String
    ) {
        val contractEntity = contractRepository.findByRegisterPropertyAndCpfBuyerAndCpfSeller(registerProperty, cpfBuyer, cpfSeller)

        contractEntity.hashContractTransaction = transactionHash

        contractRepository.save(contractEntity)
    }
}




