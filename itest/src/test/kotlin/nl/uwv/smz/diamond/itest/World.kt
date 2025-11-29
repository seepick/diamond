package nl.uwv.smz.diamond.itest

import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import nl.uwv.smz.diamond.extern.stub.posts.PostsExternStub

class World {

    private var api: WorldApi? = null
    private var client: HttpClient? = null
        set(value) {
            require(value != null)
            field = value
            api = WorldApi(value, { lastResponse = it })
        }

    private var responser: WorldResponse? = null
    private var lastResponse: HttpResponse? = null
        set(value) {
            require(value != null)
            field = value
            responser = WorldResponse(value)
        }

    lateinit var postsStub: PostsExternStub

    fun lastResponse() = responser ?: error("No last response to assert on!")

    fun api() = api ?: error("HTTP client was not yet initialized!")

    fun initContext(context: WorldContext) {
        client = context.client
        postsStub = context.resolvePostsExternStub()
    }
}
