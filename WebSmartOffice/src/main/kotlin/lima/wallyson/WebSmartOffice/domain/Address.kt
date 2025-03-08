package lima.wallyson.WebSmartOffice.domain

import java.time.LocalDateTime

data class Address (
    val streetName: String,
    val number: String,
    val complementAddress: String,
    val district: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val property: Property,
    val dateCreation: LocalDateTime = LocalDateTime.now()
)
