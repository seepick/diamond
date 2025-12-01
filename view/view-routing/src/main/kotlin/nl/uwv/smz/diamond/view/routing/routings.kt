package nl.uwv.smz.diamond.view.routing

import io.ktor.server.application.Application

internal fun Application.installRoutings() {
    installHomepageRouting()
    installInfoRouting()
    installCrystalRouting()
    installPostsRouting()
    installSyncRouting()
    installHealthRouting()
}
