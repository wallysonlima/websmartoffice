package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.ContractsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ContractRepository: JpaRepository<ContractsEntity, Long> {

    fun findByRegisterPropertyAndCpfBuyerAndCpfSeller(registerProperty: String, cpfBuyer: String, cpfSeller: String): ContractsEntity
}
