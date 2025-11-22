plugins {
    id("diamond-kotlin-common")
    id("io.ktor.plugin")
    application
}

dependencies {
    implementation(project(":view"))
    implementation(project(":logic-api"))
    implementation(project(":logic-impl"))
    implementation(project(":shared:logging"))

    implementation(Deps.ktor.server.core)
    implementation(Deps.ktor.server.netty)

//    implementation("io.insert-koin:koin-logger-slf4j:${Versions.koin}")
    implementation("io.insert-koin:koin-ktor:${Versions.koin}")
    implementation("io.insert-koin:koin-logger-slf4j:${Versions.koin}")
    implementation(Deps.logging.kotlin)
}

application {
    mainClass = "nl.uwv.smz.diamond.app.Main"
}

ktor {
    fatJar {
        archiveFileName.set("diamond.jar")
    }
}
