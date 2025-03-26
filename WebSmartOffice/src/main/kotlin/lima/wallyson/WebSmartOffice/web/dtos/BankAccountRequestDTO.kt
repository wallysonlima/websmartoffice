package lima.wallyson.WebSmartOffice.web.dtos

data class BankAccountRequestDTO (
    val bankCpf: String,
    val privateKey: String,
    val ethAddress: String
)
