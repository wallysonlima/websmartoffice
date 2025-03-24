package lima.wallyson.WebSmartOffice.web.dtos

import java.math.BigDecimal

data class BankAccountResponseDTO (
    val bankCpf: String,
    val privateKey: String,
    val ethAddress: String,
    val balance: BigDecimal
)