package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "properties")
data class PropertyEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "property_cpf")
    var propertyCpf: String? = null,

    @Column(name = "register_property", nullable = false, length = 100)
    val registerProperty:String,

    @Column(name = "notarial_deed", nullable = false, length = 100)
    val notarialDeed: String,

    @Column(nullable = false, length = 100)
    val price: Double,

    @Column(nullable = false, length = 100)
    val size: String,

    @Column(length = 300)
    val address: String? = null,

    @Column(name = "dt_creation")
    val dateCreation: LocalDateTime = LocalDateTime.now()
)
