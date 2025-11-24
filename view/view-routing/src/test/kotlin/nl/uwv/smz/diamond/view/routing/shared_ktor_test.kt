package nl.uwv.smz.diamond.view.routing

import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.JsonConvertException

inline fun <reified BODY> HttpRequestBuilder.setJsonBody(body: BODY) {
    contentType(ContentType.Application.Json)
    setBody(body)
}

suspend inline fun <reified T> HttpResponse.readBody(): T =
    try {
        body<T>()
    } catch (e: Exception) {
        val message = "Failed to deserialize response JSON to ${T::class.qualifiedName}\n<<${bodyAsText()}>>"
        throw JsonConvertException(message, e)
    }
