plugins {
    id("diamond-kotlin-common")
    id("io.ktor.plugin")
    application
}

dependencies {
    implementation(project(":view"))
    implementation(project(":logic-api"))
    implementation(project(":logic-impl"))

    implementation("io.insert-koin:koin-ktor:${Versions.koin}")
    implementation("io.insert-koin:koin-logger-slf4j:${Versions.koin}")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.github.oshai:kotlin-logging-jvm:${Versions.klogging}")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-content-negotiation")
}
