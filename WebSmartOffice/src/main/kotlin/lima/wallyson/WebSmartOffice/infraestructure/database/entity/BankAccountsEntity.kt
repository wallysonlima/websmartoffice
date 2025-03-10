package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "bank_accounts")
data class BankAccountsEntity(
        @Id
        @Column(name = "bank_cpf", nullable = false, length = 11)
        val bankCpf: String,

        @Column(name = "private_key", nullable = false)
        val privateKey: String,

        @Column(name = "ethereum_address", nullable = false)
        val ethAddress: String,

        @Column(nullable = false)
        var balance: BigDecimal,

        @Column(name = "dt_creation")
        val dateCreation: LocalDateTime = LocalDateTime.now()
)

