package lima.wallyson.WebSmartOffice.web.dtos

import java.time.LocalDate

data class PersonResponseDTO(
    val cpf: String,
    val name:String,
    val email: String,
    val phoneNumber: String,
    val dateBirth: LocalDate,
    val gender: String,
    val rg: String,
    val civilState: String,
    val bankAccount: BankAccountResponseDTO? = null,
    val role: String
)