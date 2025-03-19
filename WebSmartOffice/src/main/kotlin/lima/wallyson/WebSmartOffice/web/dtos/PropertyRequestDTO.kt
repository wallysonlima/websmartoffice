package lima.wallyson.WebSmartOffice.web.dtos

data class PropertyRequestDTO (
    val personCpf: String,
    val registerProperty: String,
    val notarialDeed: String,
    val price: Double,
    val size: String,
    val address: AddressRequestDTO
)

