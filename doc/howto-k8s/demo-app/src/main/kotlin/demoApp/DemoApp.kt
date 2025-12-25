package demoApp

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

private val log = logger {}

data class AppConfig(val port: Int) {
    companion object {
        fun load(): AppConfig {
            val errors = mutableListOf<String>()
            val port = readEnvInt(errors, "PORT")
            if (errors.isNotEmpty()) {
                throw IllegalStateException("Invalid env vars: ${errors.joinToString(";")}")
            }
            return AppConfig(
                port = port!!,
            )
        }

        private fun readEnv(errors: MutableList<String>, name: String): String? {
            val value = System.getenv(name)
            if (value == null) errors.add("Missing env var [$name]")
            return value
        }

        private fun readEnvInt(errors: MutableList<String>, name: String): Int? {
            val value = readEnv(errors, name) ?: return null
            val maybeInt = value.toIntOrNull()
            if (maybeInt == null) errors.add("Invalid int env var [$name] value: [$value]")
            return maybeInt
        }
    }
}

object DemoApp {

    init {
        log.info { "DemoApp starting up." }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val config = AppConfig.load()
        log.info { "Starting up application with: $config" }
        embeddedServer(Netty, port = config.port) {
            startKtor()
        }.start(wait = true)
    }
}

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
        // TODO add more endpoints
        // - graceful shutdown
        // - crash
        // - DB create/readAll
    }
}
