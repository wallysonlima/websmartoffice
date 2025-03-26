package lima.wallyson.WebSmartOffice.web.dtos

data class ContractResponseDTO (
    val cpfBuyer: String? = null,
    val cpfSeller: String,
    val registerProperty: String,
    val contractAddress: String,
    val hashContractTransaction:String? = null,
    val dateCreation: String
)
