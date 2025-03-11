package lima.wallyson.WebSmartOffice.infraestructure.database.repository

import lima.wallyson.WebSmartOffice.infraestructure.database.entity.AddressEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository: JpaRepository<AddressEntity, String> {}