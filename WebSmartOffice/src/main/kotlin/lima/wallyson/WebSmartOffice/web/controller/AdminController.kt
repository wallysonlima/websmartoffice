package lima.wallyson.WebSmartOffice.web.controller

import lima.wallyson.WebSmartOffice.application.usecase.PersonUseCase
import lima.wallyson.WebSmartOffice.application.usecase.PropertyUseCase
import lima.wallyson.WebSmartOffice.application.usecase.SmartContractUseCase
import lima.wallyson.WebSmartOffice.web.dtos.ContractRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonResponseDTO
import lima.wallyson.WebSmartOffice.web.dtos.PropertyRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PropertyResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val personUseCase: PersonUseCase,
    private val propertyUseCase: PropertyUseCase,
    private val smartContractUseCase: SmartContractUseCase
) {
    @PostMapping("/register/person")
    fun personRegister(
        @RequestBody request: PersonRequestDTO
    ): ResponseEntity<PersonResponseDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            personUseCase.register(request)
        )
    }

    @PostMapping("/register/property")
    fun propertyRegister(
        @RequestBody request: PropertyRequestDTO
    ): ResponseEntity<PropertyResponseDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            propertyUseCase.register(request)
        )
    }

    @DeleteMapping("/person/delete/{cpf}")
    fun personDelete(
        @PathVariable cpf: String
    ): ResponseEntity<HttpStatus> {
        return try {
            personUseCase.deletePerson(cpf)

            ResponseEntity.ok().build()
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/property/signContract")
    fun signContract(
        @RequestBody contractRequest: ContractRequestDTO
    ): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
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
}
