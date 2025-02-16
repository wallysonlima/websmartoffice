package lima.wallyson.WebSmartOffice.web.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {

    @GetMapping
    fun getUserPage(): String {
        return "Bem-vindo à área do Wallyson Lima!"
    }
}