package nl.uwv.smz.diamond.view.routing

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.KoinIsolated

fun viewTest(
    setupKoin: Module.() -> Unit = {},
    // additionalTestSetup ...
    additionalApplicationSetup: Application.() -> Unit = {},
    testCode: suspend (HttpClient) -> Unit,
) {
    testApplication {
        setupTestClient()
        setupApplication(setupKoin, additionalApplicationSetup)
        testCode(client)
    }
}

fun ApplicationTestBuilder.setupTestClient() {
    client = createClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                },
            )
        }
    }
}

fun ApplicationTestBuilder.setupApplication(
    setupKoin: Module.() -> Unit,
    additionalSetup: Application.() -> Unit,
) {
    application {
        install(KoinIsolated) {
            modules(
                module {
                    setupKoin()
                },
            )
        }
        installRoutingsAndPlugins(RoutingSetting(prettyPrint = true))
        additionalSetup()
    }
}
