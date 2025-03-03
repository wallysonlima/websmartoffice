package lima.wallyson.WebSmartOffice.application.usecase

import lima.wallyson.WebSmartOffice.infraestructure.blockchain.SimpleStorage
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import org.web3j.protocol.Web3j


@Service
class Web3Service(private val web3j: Web3j) {

    private val credentials = Credentials.create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80")

    fun deployContract(): String {
        val contract = SimpleStorage.deploy(web3j, credentials, DefaultGasProvider()).send()
        return contract.contractAddress
    }

    fun getStoredValue(contractAddress: String): BigInteger {
        val contract = SimpleStorage.load(contractAddress, web3j, credentials, DefaultGasProvider())
        return contract.get().send()
    }

    fun setStoredValue(contractAddress: String, value: BigInteger) {
        val contract = SimpleStorage.load(contractAddress, web3j, credentials, DefaultGasProvider())
        contract.set(value).send()
    }
}


