package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.ContractsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ContractRepository: JpaRepository<ContractsEntity, Long> {

    fun findByRegisterProperty(registerProperty: String): ContractsEntity
}
