package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PersonRepository : JpaRepository<PersonEntity, Long> {
    fun existsByCpf(cpf: String): Boolean

    fun findByCpf(cpf: String): PersonEntity?

    fun findByEmail(email: String): Optional<PersonEntity>

    fun deleteByCpf(cpf: String)
}