package lima.wallyson.WebSmartOffice.web.dtos

import lima.wallyson.WebSmartOffice.infraestructure.configuration.Role
import java.time.LocalDate

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
    val role: Role
)