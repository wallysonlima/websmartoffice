package lima.wallyson.WebSmartOffice.web.dtos

import lima.wallyson.WebSmartOffice.domain.Address
import java.time.LocalDate
import java.time.LocalDateTime

data class PersonRequestDTO (
    val name:String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val dateBirth: LocalDate,
    val gender: String,
    val cpf: String,
    val rg: String,
    val civilState: String,
    val isEmployee: Boolean,
    val dateCreation: LocalDateTime,
    val address: Address? = null
)