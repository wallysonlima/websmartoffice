package lima.wallyson.WebSmartOffice.domain

data class BankAccount (
    val personCpf: String,
    val numberAccount: String,
    val balance: Double,
    val person: Person
)