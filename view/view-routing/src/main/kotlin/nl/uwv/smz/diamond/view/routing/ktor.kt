package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

private val log = logger {}

data class RoutingSetting(
    /** Disable for faster speed and less bandwidth usage. */
    val prettyPrint: Boolean = false
)

fun Application.installRoutingsAndPlugins(setting: RoutingSetting) {
    log.info { "Installing Ktor routings and plugins" }
    installRoutings()
    installContentNegotiation(setting)
    installExceptionHandling()
}

internal fun Application.installContentNegotiation(setting: RoutingSetting) {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = setting.prettyPrint
                isLenient = false // be strict
                ignoreUnknownKeys = false // be strict to frontend; fail fast, fail early
            },
        )
    }
}
