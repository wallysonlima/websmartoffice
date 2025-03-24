package lima.wallyson.WebSmartOffice.web.controller

import jakarta.servlet.http.HttpServletRequest
import lima.wallyson.WebSmartOffice.application.usecase.BankAccountUseCase
import lima.wallyson.WebSmartOffice.application.usecase.PersonUseCase
import lima.wallyson.WebSmartOffice.application.usecase.PropertyUseCase
import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import lima.wallyson.WebSmartOffice.web.dtos.BuyPropertyRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonResponseDTO
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
    val bankAccountUseCase: BankAccountUseCase,
    val personUseCase: PersonUseCase
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
        @RequestParam email: String
    ): BigDecimal {
        return bankAccountUseCase.getBalanceInBrl(email)
    }

    @GetMapping("/getUser")
    fun getUserByEmail(
        @RequestParam email: String
    ): ResponseEntity<PersonResponseDTO> {
        val response = ResponseEntity.status(HttpStatus.OK).body(
            personUseCase.getPersonByEmail(email)
        )

        println("response:" + response)
        return response
    }

    @GetMapping
    fun getUser(request: HttpServletRequest): Map<String, Any> {
        val isAuthenticated = request.userPrincipal != null
        return mapOf("authenticated" to isAuthenticated, "username" to request.userPrincipal?.name) as Map<String, Any>
    }
}