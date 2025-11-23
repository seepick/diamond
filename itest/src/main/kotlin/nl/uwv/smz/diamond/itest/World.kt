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

//          DiamondSdk(client).requestHomepage()

    fun initClient(client: HttpClient) {
        log.debug { "Init Ktor client." }
        this.client = client
    }

}
