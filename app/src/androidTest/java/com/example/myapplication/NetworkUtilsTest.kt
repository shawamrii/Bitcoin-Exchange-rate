import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.concurrent.thread
import org.junit.Assert.*

class NetworkUtilsTest {
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()
    }

    @Test
    fun fetchBitcoinPriceData_returnsValidData() {
        val mockResponse = MockResponse()
        mockResponse.setBody("{\"prices\":[[1609459200000, 29000.0],[1609545600000, 29400.0]]}")
        mockResponse.setResponseCode(200)
        server.enqueue(mockResponse)

        var jsonData = ""
        val signal = CountDownLatch(1)

        NetworkUtils.fetchBitcoinPriceData("usd") { data ->
            jsonData = data
            signal.countDown()  // Notify the countdown that the data has been fetched
        }

        signal.await()  // Wait for the response to be fetched

        assertFalse("The fetched data should not be empty", jsonData.isEmpty())
        assertTrue("The fetched data should be correct", jsonData.contains("29000.0"))
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}
