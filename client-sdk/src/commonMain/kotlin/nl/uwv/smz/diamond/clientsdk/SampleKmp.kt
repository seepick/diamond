package nl.uwv.smz.diamond.clientsdk

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

// just a sample class
class SampleKmp {
    fun greetKmp() = "Hello KMP!"
}

class DiamondSdk(private val client: HttpClient = HttpClient()) {

    suspend fun greet() = "Hello SDK!"

    suspend fun requestHomepage(): String {
        return client.get("/").bodyAsText()
    }
}
