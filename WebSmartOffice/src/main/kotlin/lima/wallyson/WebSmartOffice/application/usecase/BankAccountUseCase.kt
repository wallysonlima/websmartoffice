package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.BankAccountEntity
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.BankAccountRepository
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import lima.wallyson.WebSmartOffice.web.dtos.BankAccountRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.BankAccountResponseDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BankAccountUseCase(
    private val bankAccount: BankAccountRepository,
    private val personRepository: PersonRepository
) {
    private val log: Logger = LoggerFactory.getLogger(BankAccountUseCase::class.java)

    fun register(request: BankAccountRequestDTO): BankAccountResponseDTO {
        log.info("c=BankAccountUseCase, m=register, i=init")

        val person = personRepository.findByCpf(request.personCpf)

        if ( person == null ) throw IllegalArgumentException("Erro, Pessoa não encontrada!")

        try {
            bankAccount.save(
                BankAccountEntity(
                    personCpf = person.cpf,
                    numberAccount = request.numberAccount,
                    nameBank = request.nameBank,
                    agency = request.agency,
                    balance = request.balance
                )
            )

            return BankAccountResponseDTO(
                numberAccount = request.numberAccount,
                nameBank = request.nameBank,
                agency = request.agency,
                balance = request.balance
            )

            log.info("c=BankAccountUseCase, m=register, i=end")
        } catch ( ex: Exception ) {
            log.info("c=BankAccountUseCase, m=register, i=error, m=${ex.message}")
            throw ex
        }
    }
}
