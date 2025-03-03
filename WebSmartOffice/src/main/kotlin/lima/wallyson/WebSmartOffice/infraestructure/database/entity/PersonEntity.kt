package lima.wallyson.WebSmartOffice.infraestructure.database.entity

import jakarta.persistence.*
import lima.wallyson.WebSmartOffice.infraestructure.configuration.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "person")
data class PersonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    val name:String,

    @Column(nullable = false, length = 100)
    val email: String,

    @Column(nullable = false, length = 100)
    val passwordPerson: String,

    @Column(name= "phone_number", nullable = false, length = 11)
    val phoneNumber: String,

    @Column(name = "dt_birth", nullable = false)
    val dateBirth: LocalDate,

    @Column(nullable = false, length = 100)
    val gender: String,

    @Column(nullable = false, length = 11)
    val cpf: String,

    @Column(nullable = false, length = 20)
    val rg: String,

    @Column(name = "civil_state", nullable = false, length = 100)
    val civilState: String,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @Column(name = "dt_creation")
    val dateCreation: LocalDateTime = LocalDateTime.now()
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_${role.name}"))

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}