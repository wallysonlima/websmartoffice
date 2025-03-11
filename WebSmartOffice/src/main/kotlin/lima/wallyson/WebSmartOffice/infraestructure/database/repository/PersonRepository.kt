package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PersonsEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PersonRepository : JpaRepository<PersonsEntity, String> {
    fun existsByCpf(cpf: String): Boolean

    fun findByCpf(cpf: String): PersonsEntity?

    fun findByEmail(email: String): Optional<PersonsEntity>

    fun deleteByCpf(cpf: String)
}