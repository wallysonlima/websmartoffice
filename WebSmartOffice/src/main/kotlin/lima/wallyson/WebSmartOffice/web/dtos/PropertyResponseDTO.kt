package lima.wallyson.WebSmartOffice.web.dtos

data class PropertyResponseDTO (
    val cpfProperty: String? = null,
    val registerProperty: String,
    val address: String? = null,
    val notarialDeed: String,
    val price: Double,
    val size: String,
)
