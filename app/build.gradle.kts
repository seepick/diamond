plugins {
    id("diamond-kotlin-common")
    id("io.ktor.plugin")
    application
}

dependencies {
    implementation(project(":view"))
    implementation(project(":logic:logic-api"))
    implementation(project(":logic:logic-impl"))
    implementation(project(":shared:shared-commons"))
    implementation(project(":shared:shared-logging"))

    implementation(Deps.logging.kotlin)

    implementation(Deps.ktor.server.core)
    implementation(Deps.ktor.server.netty)

    implementation(Deps.koin.ktor)
    implementation(Deps.koin.logger)
}

application {
    mainClass = "nl.uwv.smz.diamond.app.Main"
}

ktor {
    fatJar {
        archiveFileName.set("diamond.jar")
    }
}
