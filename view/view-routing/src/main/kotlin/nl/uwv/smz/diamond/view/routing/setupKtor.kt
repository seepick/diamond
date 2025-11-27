package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

private val log = logger {}

fun Application.installRoutingsAndPlugins() {
    log.info { "Installing Ktor routings and plugins" }
    installHomepageRouting()
    installCrystalRouting()
    installInfoRouting()

    installContentNegotiation()
    installExceptionHandling()
}

internal fun Application.installContentNegotiation() {
    install(ContentNegotiation) {
        // TODO disable detect warning here!
        json(Json {
            serializersModule
            prettyPrint = true
            isLenient = false
            ignoreUnknownKeys = false // be super strict (?)
        })
    }
}
