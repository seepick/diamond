package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.serialization.Serializable
import nl.uwv.smz.diamond.extern.api.posts.PostRto
import nl.uwv.smz.diamond.itest.World
import nl.uwv.smz.diamond.itest.bodyAs
import stepdefs.PostDtoRow
import stepdefs.PostRtoRow

fun PostRtoRow.toPostRto() =
    PostRto(
        id = id,
        userId = userId,
        title = title,
        completed = completed,
    )

class PostsSteps(private val world: World) {
    @Given("the following posts are returned by the backend")
    fun `Given the following posts are returned by the backend`(posts: List<PostRtoRow>) {
        world.postsStub.posts = posts.map { it.toPostRto() }
    }

    @When("get posts")
    fun `When get posts`() {
        world.api().getPosts()
    }

    @Then("response posts are")
    fun `Then response posts are`(posts: List<PostDtoRow>) {
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
