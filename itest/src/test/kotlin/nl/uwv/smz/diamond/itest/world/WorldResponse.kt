package nl.uwv.smz.diamond.itest.world

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.runBlocking
import nl.uwv.smz.diamond.shared.testKtor.readBodyByType

class WorldResponse(private val response: HttpResponse) {

    val statusCode = response.status.value

    fun bodyText() = runBlocking {
        response.bodyAsText()
    }

    fun <T> bodyByType(type: TypeInfo): T = runBlocking {
        response.readBodyByType(type)
    }
}

inline fun <reified T> WorldResponse.bodyAs() =
    bodyByType<T>(typeInfo<T>())
