package lima.wallyson.WebSmartOffice.web.dtos

data class AddressResponseDTO (
    val streetName: String,
    val number: String,
    val complementAddress: String,
    val district: String,
    val city: String,
    val state: String,
    val postalCode: String
)
