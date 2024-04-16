import android.provider.ContactsContract.CommonDataKinds.Website.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// NetworkUtils object
object NetworkUtils {
    fun fetchBitcoinPriceData(currency: String, callback: (String) -> Unit) {
        val thread = Thread {
            try {
                // Update the URL based on the selected currency
                val url = URL("https://api.coingecko.com/api/v3/coins/bitcoin/market_chart?vs_currency=$currency&days=14&interval=daily")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                reader.close()
                connection.disconnect()

                callback(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                callback("")
            }
        }
        thread.start()
    }
}
