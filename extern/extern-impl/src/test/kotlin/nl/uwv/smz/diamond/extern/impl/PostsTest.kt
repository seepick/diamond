package nl.uwv.smz.diamond.extern.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import org.openapitools.client.model.Post

// FIXME wiremock extension
class WiremockExtension {

}

class PostsTest : DescribeSpec({
    val jackson = ObjectMapper()
    val dto = PostDto(1, 2, "t", true)
    describe("GET /posts") {
        it("successful") {
            val server = WireMockServer(options().port(8089))
            server.start()

            configureFor("localhost", 8089)
            stubFor(
                get(urlEqualTo("/posts")).willReturn(
                    aResponse().withBody(
                        jackson.writeValueAsString(listOf(dto.toExternPost()))
                    )
                )
            )
            val service = PostsServiceImpl("http://localhost:8089")

            service.fetchPosts() shouldBeEqual listOf(dto)

            // server.resetAll()
            server.stop()
        }
    }
})

fun PostDto.toExternPost() = Post().also {
    it.id = id
    it.userId = userId
    it.title = title
    it.completed = completed
}
