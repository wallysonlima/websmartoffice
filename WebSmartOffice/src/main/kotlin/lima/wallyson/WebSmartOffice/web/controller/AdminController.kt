package lima.wallyson.WebSmartOffice.web.controller

import lima.wallyson.WebSmartOffice.application.usecase.AddressUseCase
import lima.wallyson.WebSmartOffice.application.usecase.BankAccountUseCase
import lima.wallyson.WebSmartOffice.application.usecase.PersonUseCase
import lima.wallyson.WebSmartOffice.application.usecase.PropertyUseCase
import lima.wallyson.WebSmartOffice.application.usecase.Web3Service
import lima.wallyson.WebSmartOffice.web.dtos.AddressRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.AddressResponseDTO
import lima.wallyson.WebSmartOffice.web.dtos.BankAccountRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.BankAccountResponseDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PersonResponseDTO
import lima.wallyson.WebSmartOffice.web.dtos.PropertyRequestDTO
import lima.wallyson.WebSmartOffice.web.dtos.PropertyResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val personUseCase: PersonUseCase,
    private val addressUseCase: AddressUseCase,
    private val propertyUseCase: PropertyUseCase,
    private val bankAccountUseCase: BankAccountUseCase
) {
    @Autowired
    lateinit var web3Service: Web3Service

    @PostMapping("/person/register")
    fun personRegister(
        @RequestBody request: PersonRequestDTO
    ): ResponseEntity<PersonResponseDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            personUseCase.register(request)
        )
    }

    @PostMapping("/address/register")
    fun addressRegister(
        @RequestBody request: AddressRequestDTO
    ): ResponseEntity<AddressResponseDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            addressUseCase.register(request)
        )
    }

    @PostMapping("/property/register")
    fun propertyRegister(
        @RequestBody request: PropertyRequestDTO
    ): ResponseEntity<PropertyResponseDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            propertyUseCase.register(request)
        )
    }

    @PostMapping("/bankAccount/register")
    fun bankAccountRegister(
        @RequestBody request: BankAccountRequestDTO
    ): ResponseEntity<BankAccountResponseDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            bankAccountUseCase.register(request)
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

    @GetMapping("/contract/get")
    fun getValue(): BigInteger {
        return web3Service.getStoredValue("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266")
    }

    @PostMapping("/contract/set")
    fun setValue(@RequestParam value: BigInteger) {
        web3Service.setStoredValue(
            "0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266",
            value
        )
    }
}
