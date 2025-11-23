plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":view:controller-api"))
    implementation(Deps.ktor.server.core)
    implementation(Deps.ktor.server.contentNegotiation)
    implementation(Deps.ktor.serialization)
    implementation(Deps.koin.ktor)
    implementation(Deps.logging.kotlin)
// TODO implementation(Deps.ktor.server.statusPages)?
    implementation(Deps.ktor.server.openApi)

    testImplementation(project(":shared:shared-test"))
    testImplementation(Deps.ktor.client.contentNegotiation)
    testImplementation(Deps.ktor.server.testHost)
}

