package nl.uwv.smz.diamond.clientsdk

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

@Suppress("unused")
class DiamondSdk(private val client: HttpClient = HttpClient()) {

    @Suppress("FunctionOnlyReturningConstant")
    fun greet() = "Hello SDK!"

    suspend fun requestHomepage(): String = client.get("/").bodyAsText()

    // IDEA: auto pagination support (hasMore); configurable as Page(pageSize, pageNumber)
}
