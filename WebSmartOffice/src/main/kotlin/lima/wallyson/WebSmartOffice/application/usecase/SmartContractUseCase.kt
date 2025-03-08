package lima.wallyson.WebSmartOffice.application.usecase

import blockchain_java.PropertySale
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import org.web3j.protocol.Web3j
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL


@Service
class SmartContractUseCase(private val web3j: Web3j) {

    fun getEthereumPrice(): BigInteger {
        val url = URL("https://api.binance.com/api/v3/ticker/price?symbol=ETHBRL")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        return try {
            val response = connection.inputStream.bufferedReader().use {it.readText() }
            val json = JSONObject(response)
            val ethereumPriceBrl = json.getString("price").toBigDecimal()
            (ethereumPriceBrl * BigDecimal(1e18)).toBigInteger()
        } catch (e: Exception) {
            throw RuntimeException("Erro ao buscar cotação ETH: ${e.message}")
        }
    }

    fun deployContract(
        numberAccount:String,
        propertyAddress: String,
        propertySize: BigInteger,
        priceInBrl: BigDecimal,
        registerProperty: String,
        notarialDeed: String
    ): String {
        val ethPriceInWei = getEthereumPrice().toBigDecimal()  // Convertendo para BigDecimal
        val priceInWei = (priceInBrl / ethPriceInWei).toBigInteger()  // Convertendo corretamente para WEI
        val credentials = Credentials.create(numberAccount)

        val contract = PropertySale.deploy(
            web3j, credentials, DefaultGasProvider(),
            propertyAddress, propertySize, priceInWei, registerProperty, notarialDeed
        ).send()

        return contract.contractAddress
    }
}


