package nl.uwv.smz.diamond.view.routing

import arrow.core.left
import arrow.core.right
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.uuid
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.mockk
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.shared.testKtor.readBody
import nl.uwv.smz.diamond.shared.testKtor.setJsonBody
import nl.uwv.smz.diamond.view.controllerApi.CrystalController
import nl.uwv.smz.diamond.view.model.ApiErrorDto
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto
import nl.uwv.smz.diamond.view.model.CrystalUpdateDto
import nl.uwv.smz.diamond.view.model.PageDto
import nl.uwv.smz.diamond.view.model.PageMetaDto
import nl.uwv.smz.diamond.view.model.PageRequestDto
import kotlin.uuid.Uuid

fun PageRequestDto.Companion.undefined() = PageRequestDto(null, null)

class CrystalRouteTest : DescribeSpec({

    val dto = Arb.crystalDto().next()
    val createDto = Arb.crystalCreateDto().next()
    val updateDto = Arb.crystalUpdateDto().next()
    val crystalId = Arb.kotlinUuid().next()
    val failure = Failure.BadRequestFailure("invalid request")
    var controller: CrystalController = mockk()

    beforeTest {
        controller = mockk()
    }

    fun crystalTest(testCode: suspend (HttpClient) -> Unit) {
        viewTest({
            single<CrystalController> { controller }
        }, testCode = testCode)
    }

    describe("GET /crystals") {
        it("Given controller returns a DTO When use page params Then ok and returned") {
            val pageRequest = PageRequestDto("1", "5")
            val page = PageDto(PageMetaDto(2, 6, false), items = listOf(dto))
            coEvery { controller.findAll(pageRequest) } returns page.right()

            crystalTest { client ->
                val response = client.get("/crystals") {
                    parameter("skip", 1)
                    parameter("take", 5)
                }

                response.status shouldBeEqual HttpStatusCode.OK
                response.readBody<PageDto<CrystalDto>>() shouldBeEqual page
            }
        }
    }

    describe("GET /crystals/{id}") {
        it("Given controller returns a DTO Then ok and returned") {
            coEvery { controller.findSingle(dto.id.toString()) } returns dto.right()

            crystalTest { client ->
                val response = client.get("/crystals/${dto.id}")

                response.status shouldBeEqual HttpStatusCode.OK
                response.readBody<CrystalDto>() shouldBeEqual dto
            }
        }
    }

    describe("POST /crystals}") {
        it("Given controller returns a DTO Then ok and returned") {
            coEvery { controller.create(createDto) } returns dto.right()
            crystalTest { client ->
                val response = client.post("/crystals") {
                    setJsonBody(createDto)
                }

                response.status shouldBeEqual HttpStatusCode.OK
                response.readBody<CrystalDto>() shouldBeEqual dto
            }
        }
        it("Given controller fails Then bad request and API error") {
            coEvery { controller.create(createDto) } returns failure.left()
            crystalTest { client ->
                val response = client.post("/crystals") {
                    setJsonBody(createDto)
                }

                response.status shouldBeEqual HttpStatusCode.BadRequest
                response.readBody<ApiErrorDto>() shouldBeEqual ApiErrorDto(
                    code = failure.code,
                    message = failure.message,
                )
            }
        }
    }

    describe("PUT /crystals/{id}") {
        it("Given controller returns DTO Then ok and DTO returned") {
            coEvery { controller.update(dto.id.toString(), updateDto) } returns dto.right()

            crystalTest { client ->
                val response = client.put("/crystals/${dto.id}") {
                    setJsonBody(updateDto)
                }

                response.status shouldBeEqual HttpStatusCode.OK
                response.readBody<CrystalDto>() shouldBeEqual dto
            }
        }
    }

    describe("DELETE /crystals/{id}") {
        it("Given controller returns a DTO Then ok and returned") {
            coEvery { controller.delete(crystalId.toString()) } returns Unit.right()

            crystalTest { client ->
                val response = client.delete("/crystals/$crystalId")

                response.status shouldBeEqual HttpStatusCode.OK
                response.bodyAsText().shouldBeEmpty()
            }
        }
        // TODO test for controller.delete fails
    }
})

fun Arb.Companion.kotlinUuid() = arbitrary {
    Uuid.parse(Arb.uuid().bind().toString())
}

fun Arb.Companion.crystalCreateDto() = arbitrary {
    CrystalCreateDto(
        weightInGram = int(1..5000).bind(),
    )
}

fun Arb.Companion.crystalUpdateDto() = arbitrary {
    CrystalUpdateDto(
        weightInGram = int(1..5000).bind(),
    )
}
