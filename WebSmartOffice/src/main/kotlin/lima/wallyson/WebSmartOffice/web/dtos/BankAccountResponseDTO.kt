package lima.wallyson.WebSmartOffice.web.dtos

data class BankAccountResponseDTO (
    val numberAccount: String,
    val nameBank: String,
    val agency: String,
    val balance: Double
)