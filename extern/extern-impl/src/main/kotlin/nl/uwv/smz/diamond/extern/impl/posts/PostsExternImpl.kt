package nl.uwv.smz.diamond.extern.impl.posts

import nl.uwv.smz.diamond.extern.api.posts.PostRto
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.openapitools.client.ApiClient
import org.openapitools.client.api.PostsApi
import org.openapitools.client.model.Post

class PostsExternImpl(baseUrl: String) : PostsExtern {
    private val http = HttpClients.createDefault()

    private val api = PostsApi(
        ApiClient(http).setBasePath(baseUrl)
    )

    override fun fetchPosts(): List<PostRto> {
        // TODO spawn coroutine
        val response = api.getPosts() // will throw on error
        return response.map { it.toPostDto() }
    }
}

private fun Post.toPostDto() =
    PostRto(
        id = id, userId = userId, title = title, completed = completed
)
