package nl.uwv.smz.diamond.itest.world

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import kotlin.contracts.ExperimentalContracts

data class CrystalRequest(
    val skip: Int?,
    val take: Int?,
    val sorts: List<String>
) {
    companion object {
        val empty = CrystalRequest(skip = null, take = null, sorts = emptyList())
    }
}

class WorldApi(
    private val client: HttpClient,
    private val responseCallback: (HttpResponse) -> Unit,
) {
    fun getHomepage() {
        runBlocking {
            responseCallback(client.get("/"))
        }
    }

    fun getCrystals(request: CrystalRequest = CrystalRequest.empty) {
        runBlocking {
            responseCallback(
                client.get("/crystals") {
                    request.skip?.also {
                        parameter("skip", it)
                    }
                    request.take?.also {
                        parameter("take", it)
                    }
                    request.sorts.ifNotEmpty {
                        parameter("sort", request.sorts.joinToString(","))
                    }
                },
            )
        }
    }

    fun getPosts() {
        runBlocking {
            responseCallback(client.get("/posts"))
        }
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.ifNotEmpty(code: (List<T>) -> Unit) {
//    contract {
//        callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
//    }
    ifEmpty { }
    if (isNotEmpty()) {
        code(this)
    }
}
