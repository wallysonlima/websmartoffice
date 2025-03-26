package lima.wallyson.WebSmartOffice.web.controller

import jakarta.servlet.http.HttpServletRequest
import lima.wallyson.WebSmartOffice.application.usecase.BankAccountUseCase
import lima.wallyson.WebSmartOffice.application.usecase.PersonUseCase
import lima.wallyson.WebSmartOffice.application.usecase.PropertyUseCase
import lima.wallyson.WebSmartOffice.application.usecase.SmartContractUseCase
import lima.wallyson.WebSmartOffice.web.dtos.BuyPropertyRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.ContractRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonResponseDTO
import lima.wallyson.WebSmartOffice.web.dtos.PropertyResponseDTO
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
    val personUseCase: PersonUseCase,
    val smartContractUseCase: SmartContractUseCase
) {

    @PostMapping("/property/buyProperty")
    fun buyProperty(
        @RequestBody buyPropertyRequest: BuyPropertyRequestDTO
    ): ResponseEntity<String> {
            return ResponseEntity.status(HttpStatus.OK).body(
                propertyUseCase.buyProperty(
                    buyPropertyRequest.cpfBuyer,
                    buyPropertyRequest.cpfSeller,
                    buyPropertyRequest.registerProperty,
                    buyPropertyRequest.buyerPrivateKey,
                    buyPropertyRequest.contractAddress
                )
            )
    }

    @PostMapping("/property/signContract")
    fun signContract(
        @RequestBody contractRequest: ContractRequestDTO
    ): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(
            smartContractUseCase.deployContract(
                contractRequest.cpfBuyer,
                contractRequest.cpfSeller,
                contractRequest.privateKeyFromBankAccount,
                contractRequest.address,
                contractRequest.propertySize,
                contractRequest.priceInBrl,
                contractRequest.registerProperty,
                contractRequest.notarialDeed,
            )
        )
    }

    @GetMapping("/balance")
    fun getBalance(
        @RequestParam cpf: String
    ): Double {
        return bankAccountUseCase.getBalanceUser(cpf)
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

    @GetMapping("/getPropertiesFromUser")
    fun getPropertiesFromUser(
        @RequestParam email: String
    ): ResponseEntity<List<PropertyResponseDTO>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            propertyUseCase.getPropertiesFromUser(email)
        )
    }

    @GetMapping("/getProperties")
    fun getProperties() : ResponseEntity<List<PropertyResponseDTO>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            propertyUseCase.getProperties()
        )
    }

    @GetMapping
    fun getUser(request: HttpServletRequest): Map<String, Any> {
        val isAuthenticated = request.userPrincipal != null
        return mapOf("authenticated" to isAuthenticated, "username" to request.userPrincipal?.name) as Map<String, Any>
    }
}