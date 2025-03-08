package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "properties")
data class PropertyEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,

    @Column(name = "register_property", nullable = false, length = 100)
    val registerProperty:String,

    @Column(name = "notarial_deed", nullable = false, length = 100)
    val notarialDeed: String,

    @Column(nullable = false, length = 100)
    val price: Double,

    @Column(nullable = false, length = 100)
    val size: String,

    @ManyToOne
    @JoinColumn(name = "pr_person_cpf", referencedColumnName = "cpf")
    val owner: PersonEntity,

    @OneToOne(mappedBy = "property", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val address: AddressEntity? = null,

    @Column(name = "dt_creation")
    val dateCreation: LocalDateTime = LocalDateTime.now()
)
