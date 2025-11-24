package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.routing.routing

private val log = logger {}

private const val OPEN_API_ENDPOINT_PATH = "openapi"
private const val OPEN_API_LOCAL_CLASSPATH = "openapi.yaml"

fun Application.installRoutings(enableOpenApi: Boolean = true) {
    log.info { "Installing routing..." }

    installHomepageRouting()
    installCrystalRouting()
    if (enableOpenApi) {
        log.debug { "Open API generation enabled." }
        routing {
            openAPI(path = OPEN_API_ENDPOINT_PATH, swaggerFile = OPEN_API_LOCAL_CLASSPATH)
        }
    }
}
