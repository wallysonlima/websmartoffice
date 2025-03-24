package lima.wallyson.WebSmartOffice.web.dtos

import lima.wallyson.WebSmartOffice.domain.BankAccount

data class PersonResponseDTO(
    val cpf: String,
    val name:String,
    val email: String,
    val bankAccount: BankAccountResponseDTO? = null,
    val role: String
)