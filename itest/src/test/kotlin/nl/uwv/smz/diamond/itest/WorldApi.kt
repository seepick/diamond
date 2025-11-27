package nl.uwv.smz.diamond.itest

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking

class WorldApi(
    private val client: HttpClient,
    private val responseCallback: (HttpResponse) -> Unit,
) {
    fun getHomepage() {
        runBlocking {
            responseCallback(client.get("/"))
        }
    }

    fun getCrystals() {
        runBlocking {
            responseCallback(client.get("/crystals"))
        }
    }
}
