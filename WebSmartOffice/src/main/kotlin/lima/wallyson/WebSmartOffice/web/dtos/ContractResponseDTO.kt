package lima.wallyson.WebSmartOffice.web.dtos

data class ContractResponseDTO (
    val contractAddress: String,
    val buyerCpf: String? = null,
    val sellerCpf: String,
    val registerProperty: String,
    val notarialDeed:String? = null,
    val dateCreated: String
)