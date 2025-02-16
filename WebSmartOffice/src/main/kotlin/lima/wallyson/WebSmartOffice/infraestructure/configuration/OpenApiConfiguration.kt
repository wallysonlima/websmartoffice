package lima.wallyson.WebSmartOffice.infraestructure.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.List


@Configuration
class OpenAPIConfiguration {
    @Bean
    fun defineOpenApi(): OpenAPI? {
        val server: Server = Server()
        server.setUrl("http://localhost:8080")
        server.setDescription("Development")

        val myContact: Contact = Contact()
        myContact.setName("Wallyson Lima")
        myContact.setEmail("wallyson.n.a.lima@gmail.com")

        val information: Info? = Info()
            .title("WEB SMART OFFICE API")
            .version("1.0")
            .description("This API exposes endpoints to manage employees.")
            .contact(myContact)
        return OpenAPI().info(information).servers(List.of<Server?>(server))
    }
}