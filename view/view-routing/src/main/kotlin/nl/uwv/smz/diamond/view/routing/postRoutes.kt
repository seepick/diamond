package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controller_api.PostsController
import org.koin.ktor.ext.inject

private val log = logger {}

internal fun Application.installPostsRouting() {
    val controller by inject<PostsController>()
    routing {
        get("/posts") {
            log.info { "GET /posts" }
            call.handle(controller.findAll())
        }
    }
}
