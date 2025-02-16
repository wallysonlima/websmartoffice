package lima.wallyson.WebSmartOffice.domain

import lima.wallyson.WebSmartOffice.infraestructure.configuration.Role
import java.time.LocalDate
import java.time.LocalDateTime

data class Person (
    val name:String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val dateBirth: LocalDate,
    val gender: String,
    val cpf: String,
    val rg: String,
    val civilState: String,
    val role: Role,
    val dateCreation: LocalDateTime,
    val bankAccount: BankAccount,
    val property: Property? = null
)
