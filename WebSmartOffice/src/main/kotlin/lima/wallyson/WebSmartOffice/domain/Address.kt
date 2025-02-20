package lima.wallyson.WebSmartOffice.domain

import java.time.LocalDateTime

data class Address (
    val personCpf: String,
    val streetName: String,
    val number: String,
    val complementAddress: String,
    val district: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val dateCreation: LocalDateTime = LocalDateTime.now()
)
