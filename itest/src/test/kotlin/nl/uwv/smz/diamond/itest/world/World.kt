package nl.uwv.smz.diamond.itest.world

import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern
import nl.uwv.smz.diamond.extern.stub.posts.PostsExternStub
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import org.koin.core.Koin

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
    lateinit var crystalRepo: CrystalRepo

    fun lastResponse() = responser ?: error("No last response to assert on!")

    fun api() = api ?: error("HTTP client was not yet initialized!")

    fun initContext(context: WorldContext) {
        client = context.client
        postsStub = context.resolvePostsExternStub()
        crystalRepo = context.resolveCrystalsRepo()
    }
}

class WorldContext(
    val client: HttpClient,
    private val koin: Koin,
) {
    fun resolvePostsExternStub(): PostsExternStub =
        koin.get<PostsExtern>() as PostsExternStub

    fun resolveCrystalsRepo() = koin.get<CrystalRepo>()
}
