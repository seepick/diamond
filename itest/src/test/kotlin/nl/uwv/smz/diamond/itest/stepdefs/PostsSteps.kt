package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
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

    // https://www.baeldung.com/cucumber-data-tables
    @Then("response posts are")
    fun `Then response posts are`(posts: List<PostDtoRow>) {
//        val posts = dataTable.asList(Post::class.java)
        world.lastResponse().bodyAs<List<PostDto>>() shouldBeEqual posts.map(PostDtoRow::toDto)
    }
// https://github.com/cucumber/cucumber-jvm/tree/main/datatable
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
