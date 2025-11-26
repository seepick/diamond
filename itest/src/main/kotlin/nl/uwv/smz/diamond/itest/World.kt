package nl.uwv.smz.diamond.itest

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class World {

    private val log = logger {}
    private lateinit var client: HttpClient
    var lastResponse: HttpResponse? = null

    suspend fun getHomepage() {
        lastResponse = client.get("/")
    }

    suspend fun getCrystals() {
        lastResponse = client.get("/crystals")
    }

    // TODO could also use DiamondSdk(client).requestHomepage() ... but then would have not enough control
    // the good old issue: one client for production use (high-level), another client (low-level) for test purpose

    fun initClient(client: HttpClient) {
        log.debug { "Init Ktor client." }
        this.client = client
    }

}
