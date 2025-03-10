package lima.wallyson.WebSmartOffice.application.usecase

import jakarta.transaction.Transactional
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.BankAccountsEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PersonsEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.BankAccountRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import lima.wallyson.WebSmartOffice.web.dtos.PersonRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonResponseDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class PersonUseCase(
    private val personRepository: PersonRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val bankAccountUseCase: BankAccountUseCase,
    private val passwordEncoder: PasswordEncoder
) {
    private val log: Logger = LoggerFactory.getLogger(PersonUseCase::class.java)
    val defaultGasPriceInBrl:BigDecimal = (0.000000218133).toBigDecimal()

    @Transactional
    fun register(request: PersonRequestDTO): PersonResponseDTO {
        log.info("c=RegisterPersonUseCase, m=register, i=init")

        if (personRepository.existsByCpf(request.cpf)) {
            throw IllegalArgumentException("Erro, CPF Já existe!")
        }

        try {
            personRepository.save(
                PersonsEntity(
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
                    BankAccountsEntity(
                        bankCpf = request.cpf,
                        privateKey = request.bankAccount?.privateKey!!,
                        ethAddress = request.bankAccount.ethAddress,
                        balance = bankAccountUseCase.getBalanceInBrl(request.bankAccount.ethAddress)
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

    @Transactional
    fun deletePerson(cpf:String) {
        val person = personRepository.findByCpf(cpf)
        if ( person == null ) IllegalArgumentException("Pessoa com o $cpf não encontrado !")

        personRepository.deleteByCpf(person?.cpf!!)
    }
}
