package nl.uwv.smz.diamond.view.routing

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

class ReadPageRequestDtoTest : StringSpec({

    fun paginationTest(query: String, expected: String) {
        viewTest(additionalApplicationSetup = {
            routing {
                get("/testPagination") {
                    call.respondText(call.request.readPageRequestDto().toString())
                }
            }
        }) { client ->
            val response = client.get("/testPagination$query")
            response.bodyAsText() shouldBeEqual expected
        }
    }
    listOf(
        "" to "PageRequestDto(skip=null, take=null)",
        "?skip=foo" to "PageRequestDto(skip=foo, take=null)",
        "?take=foo" to "PageRequestDto(skip=null, take=foo)",
        "?skip=foo&take=bar" to "PageRequestDto(skip=foo, take=bar)",
    ).forEach { (query, expected) ->
        "When request with query '$query' Then should be '$expected'" {
            paginationTest(query, expected)
        }
    }
})
