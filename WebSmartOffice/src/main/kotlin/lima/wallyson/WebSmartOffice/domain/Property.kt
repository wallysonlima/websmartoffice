package lima.wallyson.WebSmartOffice.domain

data class Property (
    val personCpf: String,
    val registerProperty: String,
    val notarialDeed: String,
    val price: Double,
    val size: String,
    val address: Address
)
