package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PersonDetailsUseCase(
    private val personRepository: PersonRepository
) : UserDetailsService {

    private val passwordEncoder = BCryptPasswordEncoder() // Criando encoder

    override fun loadUserByUsername(email: String): UserDetails {
        val person = personRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("Usuário não encontrado com e-mail: $email") }

        return User.builder()
            .username(person.email)
            .password(person.password) // Senha já criptografada no banco
            .roles(person.role.name) // Garantindo que a role do usuário seja usada
            .build()
    }

    fun authenticate(email: String, rawPassword: String): Boolean {
        val person = personRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("Usuário não encontrado com e-mail: $email") }

        val isMatch = passwordEncoder.matches(rawPassword, person.password) // ✅ Comparando senha com BCrypt
        println("Senha correta? $isMatch") // ✅ Debug para verificar a comparação
        return isMatch
    }
}