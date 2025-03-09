package lima.wallyson.WebSmartOffice.web.dtos

import java.math.BigDecimal
import java.math.BigInteger

data class ContractRequestDTO (
    val contractAddress:String,
    val propertyAddress: String,
    val propertySize: BigInteger,
    val priceInBrl: BigDecimal,
    val registerProperty: String,
    private val notarialDeed: String
)
