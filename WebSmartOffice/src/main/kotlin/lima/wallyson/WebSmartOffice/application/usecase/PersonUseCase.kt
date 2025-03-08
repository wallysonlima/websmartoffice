package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.BankAccountEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PersonEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.BankAccountRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import lima.wallyson.WebSmartOffice.web.dtos.PersonRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonResponseDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PersonUseCase(
    private val personRepository: PersonRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val log: Logger = LoggerFactory.getLogger(PersonUseCase::class.java)

    fun register(request: PersonRequestDTO): PersonResponseDTO {
        log.info("c=RegisterPersonUseCase, m=register, i=init")

        if (personRepository.existsByCpf(request.cpf)) {
            throw IllegalArgumentException("Erro, CPF Já existe!")
        }

        try {
            personRepository.save(
                PersonEntity(
                    name = request.name,
                    email = request.email,
                    passwordPerson = passwordEncoder.encode(request.password),
                    phoneNumber = request.phoneNumber,
                    dateBirth = request.dateBirth,
                    gender = request.gender,
                    cpf = request.cpf,
                    rg = request.rg,
                    role = request.role,
                    dateCreation = LocalDateTime.now(),
                    civilState = request.civilState
                )
            ).let { savedPerson ->
                bankAccountRepository.save(
                    BankAccountEntity(
                        personCpf = request.cpf,
                        numberAccount = request.bankAccount.numberAccount,
                        balance = request.bankAccount.balance,
                        person = savedPerson
                    )
                )

                log.info("c=RegisterPersonUseCase, m=register, i=end")
                return PersonResponseDTO(
                    cpf = savedPerson.cpf,
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

    fun findByEmailAndPassword(email: String, password: String): PersonEntity? {
        val person = personRepository.findByEmail(email)

        return if ( person.isPresent && passwordEncoder.matches(password, person.get().password)) {
            person.get()
        } else {
            null
        }
    }
}
