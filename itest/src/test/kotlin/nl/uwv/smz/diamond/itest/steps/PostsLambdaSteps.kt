package nl.uwv.smz.diamond.itest.steps

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java8.En
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.serialization.Serializable
import nl.uwv.smz.diamond.extern.api.posts.PostRto
import nl.uwv.smz.diamond.itest.world.World
import nl.uwv.smz.diamond.itest.world.bodyAs
import stepdefs.PostDtoRow
import stepdefs.PostRtoRow

fun PostRtoRow.toPostRto() =
    PostRto(
        id = id,
        userId = userId,
        title = title,
        completed = completed,
    )

class PostsLambdaSteps(private val world: World) : En {

    init {
        When("get posts") {
            world.api().getPosts()
        }
    }
}

class PostsSteps(private val world: World) {
    @Given("the following posts are returned by the backend")
    fun `Given the following posts are returned by the backend`(posts: List<PostRtoRow>) {
        world.postsStub.posts = posts.map { it.toPostRto() }
    }

    @Then("the response posts are")
    fun `Then the response posts are`(posts: List<PostDtoRow>) {
        world.lastResponse().bodyAs<List<PostDto>>() shouldBeEqual posts.map(PostDtoRow::toDto)
    }

    @Then("response posts are empty")
    fun `Then response posts are empty`() {
        world.lastResponse().bodyAs<List<PostDto>>().shouldBeEmpty()
    }
}

@Serializable
data class PostDto(
    var id: Int? = null,
    val title: String? = null,
)

fun PostDtoRow.toDto() =
    PostDto(
        id = id,
        title = title,
    )
