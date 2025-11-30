package nl.uwv.smz.diamond.itest.world

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking

data class CrystalRequest(
    val skip: Int?,
    val take: Int?,
) {
    companion object {
        val empty = CrystalRequest(skip = null, take = null)
    }
}

class WorldApi(
    private val client: HttpClient,
    private val responseCallback: (HttpResponse) -> Unit,
) {
    fun getHomepage() {
        runBlocking {
            responseCallback(client.get("/"))
        }
    }

    fun getCrystals(request: CrystalRequest = CrystalRequest.empty) {
        runBlocking {
            responseCallback(
                client.get("/crystals") {
                    request.skip?.also {
                        parameter("skip", it)
                    }
                    request.take?.also {
                        parameter("take", it)
                    }
                },
            )
        }
    }

    fun getPosts() {
        runBlocking {
            responseCallback(client.get("/posts"))
        }
    }
}
