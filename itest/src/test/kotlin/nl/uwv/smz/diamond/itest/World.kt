package nl.uwv.smz.diamond.itest

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse

class World {

    private val log = logger {}

    private var api: WorldApi? = null
    private var client: HttpClient? = null
        set(value) {
            require(value != null)
            field = value
            api = WorldApi(value, { lastResponse = it })
        }

    private var asserter: WorldResponse? = null
    private var lastResponse: HttpResponse? = null
        set(value) {
            require(value != null)
            field = value
            asserter = WorldResponse(value)
        }

    fun lastResponse() = asserter ?: error("No last response to assert on!")

    fun api() = api ?: error("HTTP client was not yet initialized!")

    fun initClient(client: HttpClient) {
        log.debug { "Init Ktor client." }
        this.client = client
    }

}
