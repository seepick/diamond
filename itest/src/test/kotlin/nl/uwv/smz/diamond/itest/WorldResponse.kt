package nl.uwv.smz.diamond.itest

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking

class WorldResponse(private val response: HttpResponse) {

    val statusCode = response.status.value

    fun bodyText() = runBlocking {
        response.bodyAsText()
    }
}
