package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PropertyEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PropertyRepository
import lima.wallyson.WebSmartOffice.web.dtos.PropertyRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PropertyResponseDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PropertyUseCase(
    val personRepository: PersonRepository,
    val propertyRepository: PropertyRepository
) {
    private val log: Logger = LoggerFactory.getLogger(PropertyUseCase::class.java)

    fun register(request: PropertyRequestDTO): PropertyResponseDTO {
        log.info("c=RegisterPropertyUseCase, m=register, i=init")

        val person = personRepository.findByCpf(request.personCpf)

        if (person == null) throw IllegalArgumentException("Erro, Pessoa não encontrada!")

        try {
            propertyRepository.save(
                PropertyEntity(
                    personCpf = request.personCpf,
                    registerProperty = request.registerProperty,
                    notarialDeed = request.notarialDeed,
                    price = request.price,
                    size = request.size
                )
            )

            return PropertyResponseDTO(
                registerProperty = request.registerProperty,
                notarialDeed = request.notarialDeed,
                price = request.price,
                size = request.size
            )

            log.info("c=PropertyUseCase, m=register, i=end")
        } catch (ex: Exception) {
            log.info("c=PropertyUseCase, m=register, i=error, m=${ex.message}")
            throw ex
        }
    }
}

