package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "contracts")
data class ContractsEntity (
    @Id
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
    var hashContractTransaction:String? = null
)