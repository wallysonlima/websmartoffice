package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<PersonEntity, Long> {
    fun existsByCpf(cpf: String): Boolean

    fun findByCpf(cpf: String): PersonEntity?

    fun deleteByCpf(cpf: String)
}