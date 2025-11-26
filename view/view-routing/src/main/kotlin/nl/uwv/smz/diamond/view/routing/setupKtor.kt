package nl.uwv.smz.diamond.view.routing

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

fun Application.installPlugins() {
    installContentNegotiation()
    installExceptionHandling()
}

fun Application.installContentNegotiation() {
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
