package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.BankAccountsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository: JpaRepository<BankAccountsEntity, Long> {

    fun findBankAccountByBankCpf(bankCpf:String): BankAccountsEntity
}