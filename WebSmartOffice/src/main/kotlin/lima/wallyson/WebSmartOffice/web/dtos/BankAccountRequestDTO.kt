package lima.wallyson.WebSmartOffice.web.dtos

import java.math.BigDecimal

data class BankAccountRequestDTO (
    val personCpf: String,
    val privateKey: String,
    val ethAddress: String,
    val balance: BigDecimal
)
