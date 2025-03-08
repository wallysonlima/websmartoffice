package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "bank_accounts")
data class BankAccountEntity(
        @Id
        @Column(name = "person_cpf", nullable = false, length = 11)
        val personCpf: String,

        @Column(name = "number_account", nullable = false)
        val numberAccount: String,

        @Column(nullable = false)
        val balance: Double,

        @OneToOne
        @MapsId
        @JoinColumn(name = "person_cpf", referencedColumnName = "cpf")
        val person: PersonEntity,

        @Column(name = "dt_creation")
        val dateCreation: LocalDateTime = LocalDateTime.now()
)

