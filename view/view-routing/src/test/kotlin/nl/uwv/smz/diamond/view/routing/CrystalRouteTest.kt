package nl.uwv.smz.diamond.view.routing

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.json.Json
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalDto
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

internal class CrystalRouteTest : DescribeSpec({

    val dto = Arb.crystalDto().next()
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
    /*
    val response: HttpResponse = client.post("http://localhost:8080/customer") {
    contentType(ContentType.Application.Json)
    setBody(Customer(3, "Jet", "Brains"))
     */

    describe("GET /crystals") {
        it("Given controller returns a DTO Then ok and returned") {
            every { controller.getAll() } returns listOf(dto) //Arb.list(Arb.crystalDto(), 0..10).next()

            crystalTest { client ->
                val response = client.get("/crystals")

                response.status shouldBeEqual HttpStatusCode.OK
                response.body<List<CrystalDto>>() shouldBeEqual listOf(dto)
            }
        }
    }
})
