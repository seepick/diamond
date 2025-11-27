package nl.uwv.smz.diamond.extern.impl

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.orNull
import io.kotest.property.arbitrary.string
import kotlinx.coroutines.delay
import nl.uwv.smz.diamond.shared.wiremock.WiremockListener
import nl.uwv.smz.diamond.shared.wiremock.WiremockMethod
import nl.uwv.smz.diamond.shared.wiremock.givenWiremock
import org.openapitools.client.model.Post

class PostsServiceImplTest : DescribeSpec({
    extension(WiremockListener)
    val jackson = ObjectMapper()
    val dto = Arb.postDto().next()

    describe("GET /posts") {
        it("successful") {
            givenWiremock(
                method = WiremockMethod.GET,
                path = "/posts",
                statusCode = 200,
                responseBody = jackson.writeValueAsString(listOf(dto.toExternPost()))
            )
            delay(5_000)
            PostsServiceImpl(WiremockListener.url).fetchPosts() shouldBeEqual listOf(dto)
        }
    }
})

fun Arb.Companion.postDto() = arbitrary {
    PostDto(
        id = int().bind(),
        userId = int().bind(),
        title = string().bind(),
        completed = boolean().orNull().bind(),
    )
}

fun PostDto.toExternPost() = Post().also {
    it.id = id
    it.userId = userId
    it.title = title
    it.completed = completed
}
