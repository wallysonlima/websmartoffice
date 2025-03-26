package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "contracts")
data class ContractsEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name= "cpf_buyer")
    val cpfBuyer: String? = null,

    @Column(name= "cpf_seller")
    val cpfSeller: String,

    @Column(name= "register_property")
    val registerProperty: String,

    @Column(name = "contract_address")
    val contractAddress: String,

    @Column(name = "hash_contract_transaction")
    var hashContractTransaction:String? = null,

    @Column(name = "dt_creation")
    val dateCreation: LocalDateTime = LocalDateTime.now()
)