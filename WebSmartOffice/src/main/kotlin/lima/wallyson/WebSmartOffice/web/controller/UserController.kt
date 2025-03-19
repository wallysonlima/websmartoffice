package lima.wallyson.WebSmartOffice.web.controller

import jakarta.servlet.http.HttpServletRequest
import lima.wallyson.WebSmartOffice.application.usecase.BankAccountUseCase
import lima.wallyson.WebSmartOffice.application.usecase.PropertyUseCase
import lima.wallyson.WebSmartOffice.web.dtos.BuyPropertyRequestDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/user")
class UserController(
    val propertyUseCase: PropertyUseCase,
    val bankAccountUseCase: BankAccountUseCase
) {

    @PostMapping("/buyProperty")
    fun buyProperty(
        @RequestBody buyPropertyRequest: BuyPropertyRequestDTO
    ): ResponseEntity<String> {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                propertyUseCase.buyProperty(
                    buyPropertyRequest.cpfBuyer,
                    buyPropertyRequest.cpfSeller,
                    buyPropertyRequest.registerProperty,
                    buyPropertyRequest.buyerPrivateKey,
                    buyPropertyRequest.contractAddress
                )
            )
    }

    @GetMapping("/balance")
    fun getBalance(
        @RequestParam ethereumAddress: String
    ): BigDecimal {
        return bankAccountUseCase.getBalanceInBrl(ethereumAddress)
    }

    @GetMapping
    fun getUser(request: HttpServletRequest): Map<String, Any> {
        val isAuthenticated = request.userPrincipal != null
        return mapOf("authenticated" to isAuthenticated, "username" to request.userPrincipal?.name) as Map<String, Any>
    }
}