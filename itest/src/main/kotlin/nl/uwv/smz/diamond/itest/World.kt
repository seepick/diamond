package nl.uwv.smz.diamond.itest

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.server.testing.testApplication
import nl.uwv.smz.diamond.app.prepareDiamond

class World {

    var lastResponse: HttpResponse? = null

    fun get(url: String) {
        execute { it.get(url) }
    }

    private fun execute(withClient: suspend (HttpClient) -> HttpResponse) {
        testApplication {
            application {
                prepareDiamond()
            }
            lastResponse = withClient(client)
        }
    }
}
