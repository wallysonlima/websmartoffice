package lima.wallyson.WebSmartOffice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class WebSmartOfficeApplication

fun main(args: Array<String>) {
	runApplication<WebSmartOfficeApplication>(*args)
	val encoder = BCryptPasswordEncoder()
	val rawPassword = "1234"
	val hashedPassword = encoder.encode(rawPassword)

	println("Senha criptografada: $hashedPassword")
}
