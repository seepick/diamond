package nl.uwv.smz.diamond.shared.testKtor

import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.JsonConvertException
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

inline fun <reified BODY> HttpRequestBuilder.setJsonBody(body: BODY) {
    contentType(ContentType.Application.Json)
    setBody(body)
}

/** Ensures a proper exception thrown containing info about the target type and the attempted JSON to parse. */
suspend inline fun <reified T> HttpResponse.readBody(): T =
    readBodyByType(typeInfo<T>())

/**
 * The non-inline version.
 * @see readBody
 */
@Suppress("TooGenericExceptionCaught")
suspend fun <T> HttpResponse.readBodyByType(type: TypeInfo): T =
    try {
        body(type)
    } catch (e: Exception) {
        val message = "Failed to deserialize response JSON to ${type.type.qualifiedName}\n<<${bodyAsText()}>>"
        throw JsonConvertException(message, e)
    }
