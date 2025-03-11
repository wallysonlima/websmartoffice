package lima.wallyson.WebSmartOffice.web.dtos

data class BuyPropertyRequestDTO (
    val cpfBuyer: String,
    val cpfSeller: String,
    val registerProperty: String,
    val buyerPrivateKey: String,
    val contractAddress: String
)
