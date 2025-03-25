package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.PropertyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PropertyRepository: JpaRepository<PropertyEntity, Long> {

    fun findAllByPropertyCpf(propertyCpf: String): List<PropertyEntity>
}