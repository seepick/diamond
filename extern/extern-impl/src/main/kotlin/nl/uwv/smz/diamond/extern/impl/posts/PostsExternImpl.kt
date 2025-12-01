package nl.uwv.smz.diamond.extern.impl.posts

import nl.uwv.smz.diamond.extern.api.posts.PostRto
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern
import nl.uwv.smz.diamond.extern.impl.safeMeasureSuccessful
import nl.uwv.smz.diamond.shared.common.ServiceHealthInfo
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.openapitools.client.ApiClient
import org.openapitools.client.api.PostsApi
import org.openapitools.client.model.Post

class PostsExternImpl(baseUrl: String) : PostsExtern {
    private val http = HttpClients.createDefault()

    private val api = PostsApi(
        ApiClient(http).setBasePath(baseUrl),
    )

    override fun fetchPosts(): List<PostRto> {
        // TODO spawn coroutine
        val response = api.posts // will throw on error
        return response.map { it.toPostDto() }
    }

    override fun healthInfo(): ServiceHealthInfo {
        val (time, state) = safeMeasureSuccessful { api.posts }
        return ServiceHealthInfo(
            serviceName = SERVICE_HEALTH_NAME,
            pingTimeInMs = time,
            state = state,
        )
    }

    companion object {
        private const val SERVICE_HEALTH_NAME = "Posts API"
    }
}

private fun Post.toPostDto() =
    PostRto(
        id = id,
        userId = userId,
        title = title,
        completed = completed,
    )
