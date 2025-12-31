package demoApp

import demoApp.db.ItemRepo
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

private val log = logger {}

fun Application.startKtor() {
    log.debug { "Simulating delayed startup time (by 10secs)" }
    Thread.sleep(10_000)
    log.debug { "... sleeping done; service will be ready." }

    routing {
        get("/") {
            log.debug { "Handling request for GEt /" }
            val msg = StringBuilder()
            msg.append("Hello World!\n\n")
            val envs = System.getenv()
            msg.append("Environment Variables (${envs.size}):\n\n")
            msg.append(envs.map { "- ${it.key} = ${it.value}" }.joinToString("\n"))
            call.respondText(msg.toString())
        }
        get("/health") {
            log.debug { "Handling request for GET /health" }
            call.respond(HttpStatusCode.OK)
        }
        get("/ready") {
            log.debug { "Handling request for GET /ready" }
            call.respond(HttpStatusCode.OK)
        }
        get("/items") {
            log.debug { "Handling request for GET /items" }
            val items = ItemRepo.selectAll()
            val message = "Items:\n\n${items.joinToString("\n") { "- ${it.name} [${it.id}]" }}"
            call.respondText(message)
        }
        get("/createItem") {
            log.debug { "Handling request for GET /createItem" }
            ItemRepo.insertRandom()
            call.respond(HttpStatusCode.OK)
        }
        // TODO add more endpoints
        // /graceful-shutdown
        // /crash
    }
}
