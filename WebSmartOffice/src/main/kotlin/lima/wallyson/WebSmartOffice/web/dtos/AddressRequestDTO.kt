package lima.wallyson.WebSmartOffice.web.dtos

import lima.wallyson.WebSmartOffice.domain.Address

data class AddressRequestDTO (
    val cpf: String,
    val personCpf: String,
    val streetName: String,
    val number: String,
    val complementAddress: String,
    val district: String,
    val city: String,
    val state: String,
    val postalCode: String
)