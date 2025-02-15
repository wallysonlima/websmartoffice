package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PersonEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import lima.wallyson.WebSmartOffice.web.dtos.PersonRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonResponseDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

class PersonUseCase(
    private val personRepository: PersonRepository,

    private val passwordEncoder: PasswordEncoder
) {
    private val log: Logger = LoggerFactory.getLogger(PersonUseCase::class.java)

    fun register(person: PersonRequestDTO): PersonResponseDTO {
        log.info("c=RegisterPersonUseCase, m=register, i=init")

        if (personRepository.existsByCpf(person.cpf)) {
            throw IllegalArgumentException("Erro, CPF Já existe!")
        }

        try {
            personRepository.save(
                PersonEntity(
                    name = person.name,
                    email = person.email,
                    password = passwordEncoder.encode(person.password),
                    phoneNumber = person.phoneNumber,
                    dateBirth = person.dateBirth,
                    gender = person.gender,
                    cpf = person.cpf,
                    rg = person.rg,
                    isEmployee = false,
                    dateCreation = LocalDateTime.now(),
                    civilState = person.civilState
                )
            ).let { savedPerson ->
                log.info("c=RegisterPersonUseCase, m=register, i=end")
                return PersonResponseDTO(
                    id = savedPerson.id,
                    name = savedPerson.name,
                    email = savedPerson.email
                )
            }
        } catch (ex: Exception) {
            log.info("c=RegisterPersonUseCase, m=register, i=error, m=${ex.message}")
            throw ex
        }
    }

    fun deletePerson(cpf:String) {
        val person = personRepository.findByCpf(cpf)
        if ( person == null ) IllegalArgumentException("Pessoa com o $cpf não encontrado !")

        personRepository.deleteByCpf(person?.cpf!!)
    }
}
