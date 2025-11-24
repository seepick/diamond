package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application

private val log = logger {}

fun Application.installRoutings() {
    log.info { "Installing routing..." }
    installHomepageRouting()
    installCrystalRouting()
}
