package lima.wallyson.WebSmartOffice.domain

import java.time.LocalDate
import java.time.LocalDateTime

data class Person (
    val name:String,
    val password: String,
    val phoneNumber: String,
    val dateBirth: LocalDate,
    val gender: String,
    val cpf: String,
    val rg: String,
    val civilState: String,
    val isEmployee: Boolean,
    val dateCreation: LocalDateTime
)
