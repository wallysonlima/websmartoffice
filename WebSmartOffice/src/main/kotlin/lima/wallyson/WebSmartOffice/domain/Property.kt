package lima.wallyson.WebSmartOffice.domain

data class Property (
    val registerProperty: String,
    val notarialDeed: String,
    val price: Double,
    val size: String,
    val owner: Person,
    val address: Address? = null
)
