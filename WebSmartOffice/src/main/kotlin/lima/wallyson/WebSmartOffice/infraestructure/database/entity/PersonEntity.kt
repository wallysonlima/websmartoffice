package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "person")
data class PersonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    val name:String,

    @Column(nullable = false, length = 100)
    val email: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(name= "phone_number", nullable = false, length = 11)
    val phoneNumber: String,

    @Column(name = "dt_birth", nullable = false)
    val dateBirth: LocalDate,

    @Column(nullable = false, length = 100)
    val gender: String,

    @Column(nullable = false, length = 11)
    val cpf: String,

    @Column(nullable = false, length = 20)
    val rg: String,

    @Column(name = "civil_state", nullable = false, length = 100)
    val civilState: String,

    @Column(name = "is_employee", nullable = false)
    val isEmployee: Boolean,

    @Column(name = "dt_creation")
    val dateCreation: LocalDateTime = LocalDateTime.now()
)