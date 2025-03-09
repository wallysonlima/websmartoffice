package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.BankAccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository: JpaRepository<BankAccountEntity, Long> {

    fun findBankAccountByPersonCpf(personCpf:String): BankAccountEntity
}