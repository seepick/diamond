package nl.uwv.smz.diamond.view.routing

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.mockk.every
import io.mockk.mockk
import nl.uwv.smz.diamond.shared.testKtor.readBody
import nl.uwv.smz.diamond.view.controllerApi.InfoController
import nl.uwv.smz.diamond.view.model.InfoDto
import java.time.LocalDateTime

class InfoRouteTest : StringSpec({

    val dto = InfoDto(
        version = "test",
        buildTime = LocalDateTime.of(2000, 1, 1, 12, 42),
    )
    var controller: InfoController = mockk()

    beforeTest {
        controller = mockk()
    }

    fun infoTest(testCode: suspend (HttpClient) -> Unit) {
        viewTest({
            single<InfoController> { controller }
        }, testCode = testCode)
    }

    "Given controller returns a DTO Then ok and returned" {
        every { controller.info() } returns dto

        infoTest { client ->
            val response = client.get("/info")

            response.status shouldBeEqual HttpStatusCode.OK
            response.readBody<InfoDto>() shouldBeEqual dto
        }
    }
})
