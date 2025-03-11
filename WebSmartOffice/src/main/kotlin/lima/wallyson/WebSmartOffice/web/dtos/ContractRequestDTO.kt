package lima.wallyson.WebSmartOffice.web.dtos

import java.math.BigDecimal
import java.math.BigInteger

data class ContractRequestDTO (
    val cpfBuyer: String,
    val cpfSeller: String,
    val privateKeyFromBankAccount:String,
    val address: String,
    val propertySize: BigInteger,
    val priceInBrl: BigDecimal,
    val registerProperty: String,
    val notarialDeed: String
)
