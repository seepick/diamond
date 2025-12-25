package demoApp

import io.ktor.http.HttpStatusCode
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

object DemoApp {
    @JvmStatic
    fun main(args: Array<String>) {
        embeddedServer(Netty, port = 8080) {
            println("Simulating delayed startup time (by 10secs)")
            Thread.sleep(10_000)
            println("... sleeping done; service will be ready.")

            routing {
                get("/") {
                    val msg = StringBuilder()
                    msg.append("Hello World!\n\n")
                    val envs = System.getenv()
                    msg.append("Environment Variables (${envs.size}):\n\n")
                    msg.append(envs.map { "- ${it.key} = ${it.value}" }.joinToString("\n"))
                    call.respondText(msg.toString())
                }
                get("/health") {
                    call.respond(HttpStatusCode.OK)
                }
                get("/ready") {
                    call.respond(HttpStatusCode.OK)
                }
            }
        }.start(wait = true)
    }
}
