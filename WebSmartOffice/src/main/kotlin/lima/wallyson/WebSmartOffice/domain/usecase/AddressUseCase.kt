package lima.wallyson.WebSmartOffice.domain.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.AddressEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.AddressRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import lima.wallyson.WebSmartOffice.web.dtos.AddressRequestDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AddressUseCase(
    private val addressRepository: AddressRepository,
    private val personRepository: PersonRepository
) {
    private val log: Logger = LoggerFactory.getLogger(AddressUseCase::class.java)

    fun register(request: AddressRequestDTO) {
        log.info("c=RegisterPersonUseCase, m=registerAddress, i=init")

        val person = personRepository.findByCpf(request.cpf)

        if ( person == null ) throw IllegalArgumentException("Erro, Pessoa não encontrada!")

        try {
            addressRepository.save(
                AddressEntity(
                    personCpf = person.cpf,
                    streetName = request.streetName,
                    number = request.number,
                    complementAddress = request.complementAddress,
                    district = request.district,
                    city = request.city,
                    state = request.state,
                    postalCode = request.postalCode,
                )
            )

            log.info("c=RegisterPersonUseCase, m=registerAddress, i=end")
        } catch ( ex: Exception ) {
            log.info("c=RegisterPersonUseCase, m=registerAddress, i=error, m=${ex.message}")
            throw ex
        }
    }
}