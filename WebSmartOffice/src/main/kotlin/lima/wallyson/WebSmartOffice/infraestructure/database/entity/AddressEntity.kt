package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "address")
data class AddressEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    @Column(name = "person_id", nullable = false)
    val personId: Int,

    @Column(name= "street_name", nullable = false, length = 255)
    val streetName: String,

    @Column(nullable = false, length = 10)
    val number: String,

    @Column(name = "complement_address", nullable = false, length = 100)
    val complemnentAddress: String,

    @Column(nullable = false, length = 255)
    val district: String,

    @Column(nullable = false, length = 100)
    val city: String,

    @Column(nullable = false, length = 100)
    val state: String,

    @Column(name = "postal_code", nullable = false, length = 8)
    val postalCode: String,

    @Column(name = "dt_creation")
    val dateCreation: LocalDateTime = LocalDateTime.now()
)