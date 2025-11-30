package nl.uwv.smz.diamond.itest.world

import arrow.core.fold
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern
import nl.uwv.smz.diamond.extern.stub.posts.PostsExternStub
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import org.koin.core.Koin

class Variables {
    private val data = mutableMapOf<String, String>()

    operator fun get(key: String) = data[key]

    operator fun set(key: String, value: String) {
        data[key] = value
    }

    fun process(text: String): String =
        data.fold(text) { t, (key, value) ->
            t.replace("\$$key", value)
        }
}

class World {

    var variables = Variables()
        private set

    private var api: WorldApi? = null
    private var client: HttpClient? = null
        set(value) {
            require(value != null)
            field = value
            api = WorldApi(value, { lastResponse = it })
        }

    private var response: WorldResponse? = null
    private var lastResponse: HttpResponse? = null
        set(value) {
            require(value != null)
            field = value
            response = WorldResponse(value)
        }

    lateinit var postsStub: PostsExternStub
    lateinit var crystalRepo: CrystalRepo

    fun lastResponse() = response ?: error("No last response to assert on!")

    fun api() = api ?: error("HTTP client was not yet initialized!")

    fun reinitialize(context: WorldContext) {
        client = context.client
        postsStub = context.resolvePostsExternStub()
        crystalRepo = context.resolveCrystalsRepo()
        variables = Variables()
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
