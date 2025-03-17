package lima.wallyson.WebSmartOffice.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
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
            println("✅ Usuário autenticado com sucesso: ${user.username}")

            return ResponseEntity.ok(mapOf("email" to user.username, "roles" to user.authorities))
        } catch (e: Exception) {
            println("❌ Erro na autenticação: ${e.message}")
            return ResponseEntity.status(401).body("Usuário ou senha inválidos")
        }
    }

//    @PostMapping("/logout")
//    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<String> {
//        val authentication = SecurityContextHolder.getContext().authentication
//
//        if (authentication == null || !authentication.isAuthenticated) {
//            println("❌ Nenhum usuário autenticado para fazer logout!")
//            return ResponseEntity.status(403).body("Nenhum usuário autenticado para logout")
//        }
//
//        println("🔹 Usuário autenticado: ${authentication.name}, realizando logout...")
//
//        // Remove autenticação e invalida a sessão
//        SecurityContextLogoutHandler().logout(request, response, authentication)
//
//        return ResponseEntity.ok("Logout realizado com sucesso!")
//    }
//
//    @GetMapping("/me")
//    fun getCurrentUser(): ResponseEntity<Any> {
//        val authentication = SecurityContextHolder.getContext().authentication
//        return if (authentication.isAuthenticated) {
//            ResponseEntity.ok(mapOf("user" to authentication.name, "roles" to authentication.authorities))
//        } else {
//            ResponseEntity.status(401).body("Usuário não autenticado")
//        }
//    }
}

data class LoginRequest(val email: String, val password: String)
