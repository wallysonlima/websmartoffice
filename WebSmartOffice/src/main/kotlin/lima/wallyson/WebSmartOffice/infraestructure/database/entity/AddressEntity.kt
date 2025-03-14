package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "addresses")
data class AddressEntity (
    @Id
    @Column(name= "property_register")
    val propertyRegister: String? = null,

    @Column(name= "street_name", nullable = false, length = 255)
    val streetName: String,

    @Column(nullable = false, length = 10)
    val number: String,

    @Column(name = "complement_address", nullable = false, length = 100)
    val complementAddress: String,

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