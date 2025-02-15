package lima.wallyson.WebSmartOffice.web.dtos

data class BankAccountRequestDTO (
    val personCpf: String,
    val numberAccount: String,
    val nameBank: String,
    val agency: String,
    val balance: Double
)
