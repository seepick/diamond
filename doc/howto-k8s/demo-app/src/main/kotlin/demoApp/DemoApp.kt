package demoApp

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

object DemoApp {
    @JvmStatic
    fun main(args: Array<String>) {
        embeddedServer(Netty, port = 8080) {
            routing {
                get("/") {
                    val msg = StringBuilder()
                    msg.append("Hello World!\n\n")
                    val envs = System.getenv()
                    msg.append("Environment Variables (${envs.size}):\n\n")
                    msg.append(envs.map { "- ${it.key} = ${it.value}" }.joinToString("\n"))
                    call.respondText(msg.toString())
                }
            }
        }.start(wait = true)
    }
}
