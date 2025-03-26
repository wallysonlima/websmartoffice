package lima.wallyson.WebSmartOffice.web.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager // Agora o Spring consegue injetar!
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        println("🔹 Tentando autenticar usuário: ${loginRequest.email}")

        try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
            )

            SecurityContextHolder.getContext().authentication = authentication

            val user = authentication.principal as org.springframework.security.core.userdetails.User
            val roles = user.authorities.map { it.authority }
            println("✅ Usuário autenticado com sucesso: ${user.username}")

            return ResponseEntity.ok(mapOf("email" to loginRequest.email, "roles" to roles))
        } catch (e: Exception) {
            println("❌ Erro na autenticação: ${e.message}")
            return ResponseEntity.status(401).body("Usuário ou senha inválidos")
        }
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<String> {
        // Remove autenticação e invalida a sessão

        return ResponseEntity.ok("Logout realizado com sucesso!")
    }

    @GetMapping("/me")
    fun getCurrentUser(): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication.isAuthenticated) {
            ResponseEntity.ok(mapOf("user" to authentication.name, "roles" to authentication.authorities))
        } else {
            ResponseEntity.status(401).body("Usuário não autenticado")
        }
    }
}

data class LoginRequest(val email: String, val password: String)
