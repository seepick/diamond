package nl.uwv.smz.diamond.extern.impl

import org.apache.hc.client5.http.impl.classic.HttpClients
import org.openapitools.client.ApiClient
import org.openapitools.client.api.PostsApi
import org.openapitools.client.model.Post


data class PostDto(
    val id: Int, val userId: Int, val title: String, val completed: Boolean? = null
)

class PostsServiceImpl(baseUrl: String) {
    val http = HttpClients.createDefault()
    //    val http = HttpClientBuilder.create().build()
    private val api = PostsApi(
        ApiClient(http).setBasePath(baseUrl)
    )

    fun fetchPosts(): List<PostDto> {
        // spawn coroutine
        val response = api.getPosts() // throws exception if fail => translate to either
//        check(response.success) { "Invalid status code: ${response.status}" }
        return response.map { it.toPostDto() }
    }
}

private fun Post.toPostDto() = PostDto(
    id = id, userId = userId, title = title, completed = completed
)
