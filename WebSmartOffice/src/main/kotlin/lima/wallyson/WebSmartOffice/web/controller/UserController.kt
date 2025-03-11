package lima.wallyson.WebSmartOffice.web.controller

import lima.wallyson.WebSmartOffice.application.usecase.PropertyUseCase
import lima.wallyson.WebSmartOffice.web.dtos.BuyPropertyRequestDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    val propertyUseCase: PropertyUseCase
) {

    @PostMapping
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
}