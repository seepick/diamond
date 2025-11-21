plugins {
    id("diamond-kotlin-common")
//    kotlin("plugin.serialization")
    id("io.ktor.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}
dependencies {
    implementation(project(":logic-api"))
    implementation("io.insert-koin:koin-ktor:${Versions.koin}")
    implementation("io.insert-koin:koin-logger-slf4j:${Versions.koin}")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-content-negotiation")
//    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
//    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
//    implementation("com.h2database:h2:${Versions.h2}")
    implementation("io.github.oshai:kotlin-logging-jvm:${Versions.klogging}")
    implementation("io.ktor:ktor-server-host-common")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-netty")
//    implementation("ch.qos.logback:logback-classic:${Versions.logback}")
    testImplementation("io.ktor:ktor-server-test-host")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

