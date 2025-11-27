package nl.uwv.smz.diamond.itest

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.runBlocking

class WorldResponse(private val response: HttpResponse) {

    val statusCode = response.status.value

    fun bodyText() = runBlocking {
        response.bodyAsText()
    }

    fun <T> bodyByType(type: TypeInfo): T =
        runBlocking {
            response.body(type)
        }
}

inline fun <reified T> WorldResponse.bodyAs() =
    runBlocking {
        bodyByType<T>(typeInfo<T>())
}
