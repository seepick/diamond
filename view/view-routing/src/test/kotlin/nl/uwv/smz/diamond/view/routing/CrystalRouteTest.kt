package nl.uwv.smz.diamond.view.routing

import arrow.core.left
import arrow.core.right
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.uuid
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.json.Json
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.model.ApiErrorDto
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto
import nl.uwv.smz.diamond.view.model.CrystalUpdateDto
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import kotlin.uuid.Uuid

object LogListener : BeforeProjectListener {
    override suspend fun beforeProject() {
        // FIXME logging for kotest?!
        println("before xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    }
}

internal class CrystalRouteTest : DescribeSpec({

    extensions(LogListener)
    val dto = Arb.crystalDto().next()
    val createDto = Arb.crystalCreateDto().next()
    val updateDto = Arb.crystalUpdateDto().next()
    val crystalId = Arb.kotlinUuid().next()
    val failure = Failure.BadRequestFailure("invalid request")
    var controller: CrystalController = mockk()

    beforeTest {
        controller = mockk()
    }

    fun crystalTest(code: suspend (HttpClient) -> Unit) {
        testApplication {
            client = createClient {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true // be super strict (?)
                    })
                }
            }
            application {

                install(Koin) {
                    modules(module {
                        single<CrystalController> { controller }
                    })
                }
                setupFundamentalKtorFeatures()
                installCrystalRouting()
            }
            code(client)
        }
    }

    describe("GET /crystals") {
        it("Given controller returns a DTO Then ok and returned") {
            every { controller.findAll() } returns listOf(dto).right()

            crystalTest { client ->
                val response = client.get("/crystals")

                response.status shouldBeEqual HttpStatusCode.OK
                response.body<List<CrystalDto>>() shouldBeEqual listOf(dto)
            }
        }
    }

    describe("GET /crystals/{id}") {
        it("Given controller returns a DTO Then ok and returned") {
            every { controller.findSingle(dto.id) } returns dto.right()

            crystalTest { client ->
                val response = client.get("/crystals/${dto.id}")

                response.status shouldBeEqual HttpStatusCode.OK
                response.body<CrystalDto>() shouldBeEqual dto
            }
        }
    }

    describe("POST /crystals}") {
        it("Given controller returns a DTO Then ok and returned") {
            every { controller.create(createDto) } returns dto.right()
            crystalTest { client ->
                val response = client.post("/crystals") {
                    setJsonBody(createDto)
                }

                response.status shouldBeEqual HttpStatusCode.OK
                response.body<CrystalDto>() shouldBeEqual dto
            }
        }
        it("Given controller fails Then bad request and API error") {
            every { controller.create(createDto) } returns failure.left()
            crystalTest { client ->
                val response = client.post("/crystals") {
                    setJsonBody(createDto)
                }

                response.status shouldBeEqual HttpStatusCode.BadRequest
                response.body<ApiErrorDto>() shouldBeEqual ApiErrorDto(code = failure.code, message = failure.message)
            }
        }
    }

    describe("PUT /crystals/{id}") {
        it("Given controller returns DTO Then ok and DTO returned") {
            every { controller.update(dto.id, updateDto) } returns dto.right()

            crystalTest { client ->
                val response = client.put("/crystals/${dto.id}") {
                    setJsonBody(updateDto)
                }

                response.status shouldBeEqual HttpStatusCode.OK
                response.body<CrystalDto>() shouldBeEqual dto
            }
        }
    }

    describe("DELETE /crystals/{id}") {
        it("Given controller returns a DTO Then ok and returned") {
            every { controller.delete(crystalId.toString()) } returns Unit.right()

            crystalTest { client ->
                val response = client.delete("/crystals/${crystalId}")

                response.status shouldBeEqual HttpStatusCode.OK
                response.bodyAsText().shouldBeEmpty()
            }
        }
        // TODO test for controller.delete fails
    }
})

// TODO move to shared-test
inline fun <reified BODY> HttpRequestBuilder.setJsonBody(body: BODY) {
    contentType(ContentType.Application.Json)
    setBody(body)
}

fun Arb.Companion.kotlinUuid() = arbitrary {
    Uuid.parse(Arb.uuid().next().toString())
}

fun Arb.Companion.crystalCreateDto() = arbitrary {
    CrystalCreateDto(
        weightInGram = int(1..5000).next()
    )
}

fun Arb.Companion.crystalUpdateDto() = arbitrary {
    CrystalUpdateDto(
        weightInGram = int(1..5000).next()
    )
}
