package lima.wallyson.WebSmartOffice.domain

data class BankAccount (
    val personCpf: String,
    val numberAccount: String,
    val nameBank: String,
    val agency: String,
    val balance: Double
)