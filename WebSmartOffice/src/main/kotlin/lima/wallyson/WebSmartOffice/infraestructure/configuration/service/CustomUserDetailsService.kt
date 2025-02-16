package lima.wallyson.WebSmartOffice.infraestructure.configuration.service

import lima.wallyson.WebSmartOffice.infraestructure.database.repository.PersonRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val personRepository: PersonRepository
): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        return personRepository.findByEmail(email).orElseThrow { NotFoundException() }
    }
}
