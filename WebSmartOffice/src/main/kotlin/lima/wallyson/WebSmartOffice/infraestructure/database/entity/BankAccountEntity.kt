package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "bank")
data class BankAccountEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Long? = null,

        @Column(name = "person_cpf", nullable = false)
        val personCpf: String,

        @Column(name = "number_account", nullable = false)
        val numberAccount: String,

        @Column(name = "name_bank", nullable = false)
        val nameBank: String,

        @Column(nullable = false)
        val agency: String,

        @Column(nullable = false)
        val balance: Double,

        @Column(name = "dt_creation")
        val dateCreation: LocalDateTime = LocalDateTime.now()
)

