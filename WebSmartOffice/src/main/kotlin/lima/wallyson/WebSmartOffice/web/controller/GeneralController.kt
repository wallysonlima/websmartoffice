package lima.wallyson.WebSmartOffice.web.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GeneralController {

    @Tag(name = "get", description = "GET methods of HELLO")
    @GetMapping("/hello")
    fun sayHello(): String {
        return "HEllo World Wallyson Lima!"
    }
}