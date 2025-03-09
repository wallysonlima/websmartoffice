package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PersonDetailsUseCase(
    private val personRepository: PersonRepository
): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        try {
            return personRepository.findByEmail(email).get()
        } catch(ex: UsernameNotFoundException) {
            throw UsernameNotFoundException("Usuário não encontrado com e-mail: $email")
        }
    }
}