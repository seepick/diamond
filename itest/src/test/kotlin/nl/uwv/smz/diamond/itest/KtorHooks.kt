package nl.uwv.smz.diamond.itest

import com.sksamuel.hoplite.Masked
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.TestApplication
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import nl.uwv.smz.diamond.app.BuildProperties
import nl.uwv.smz.diamond.app.EnvConfig
import nl.uwv.smz.diamond.app.GlobalConfig
import nl.uwv.smz.diamond.app.KtorConfig
import nl.uwv.smz.diamond.app.setupDiamondKtor
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern
import nl.uwv.smz.diamond.extern.impl.ExternConfig
import nl.uwv.smz.diamond.extern.stub.externStub
import nl.uwv.smz.diamond.extern.stub.posts.PostsExternStub
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.core.Koin
import org.koin.ktor.plugin.KOIN_ATTRIBUTE_KEY
import java.time.LocalDateTime
import kotlin.coroutines.EmptyCoroutineContext

class KtorHooks(private val world: World) {

    private val log = logger {}
    private var testApplication: TestApplication? = null
    private val testEnvConfig = EnvConfig(
        KtorConfig(),
        DatabaseConfig(
            jdbcUrl = "jdbc:h2:mem:testdb${System.currentTimeMillis()};DB_CLOSE_DELAY=-1",
            username = "",
            password = Masked(""),
        ),
        ExternConfig("postsUrl"), // not used, as using stub
    )
    private val testGlobalConfig = GlobalConfig(
        testEnvConfig,
        BuildProperties(
            "0-test",
            LocalDateTime.of(2000, 1, 1, 12, 42)
        )
    )

    @Before
    fun `before each scenario`(scenario: Scenario) {
        log.trace { "Starting ktor for test: ${scenario.name}" }
        var clientt: HttpClient? = null
        startKtor {
            client =
                createClient {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                serializersModule
                                prettyPrint = true
                                isLenient = false
                                ignoreUnknownKeys = false // be super strict (?)
                            },
                        )
                    }
                }
            clientt = client
            application {
                setupDiamondKtor(testGlobalConfig, Modules.externStub())
            }
        }
        val koin = (testApplication!!.application.attributes.get(KOIN_ATTRIBUTE_KEY) as Koin)
        world.initContext(WorldContext(clientt!!, (koin.get<PostsExtern>() as PostsExternStub)))
    }

    // TODO report to ktor people, using with cucumber, "delocated" shutdown of ktor test application context
    @Suppress("INVISIBLE_REFERENCE", "ERROR_SUPPRESSION") // FIXME this is a hack :-/
    private fun startKtor(block: suspend ApplicationTestBuilder.() -> Unit) = runBlocking {
        val builder = ApplicationTestBuilder()
        with(builder) {
            withContext(EmptyCoroutineContext) { block() }
        }
        testApplication = builder.testApplication.also {
            it.start()
        }
    }

    @After
    fun `after each scenario`(scenario: Scenario): Unit =
        runBlocking {
            log.trace { "stop ktor for: ${scenario.name}" }
        testApplication?.stop()
    }
}

data class WorldContext(
    val client: HttpClient,
    val postsStub: PostsExternStub,
)
