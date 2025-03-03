package lima.wallyson.WebSmartOffice.infraestructure.blockchain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

@Configuration
class BlockchainConfig {
    @Bean
    fun web3j(): Web3j {
        return Web3j.build(HttpService("http://127.0.0.1:8545"))
    }
}
