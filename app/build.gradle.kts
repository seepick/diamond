plugins {
    id("diamond-kotlin-common")
    id("io.ktor.plugin")
    application
}

dependencies {
    implementation(project(":view"))
    implementation(project(":domain:domain-logic-impl"))
    implementation(project(":view:view-routing"))
    implementation(project(":view:controller-impl"))
    implementation(project(":shared:shared-common"))
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
