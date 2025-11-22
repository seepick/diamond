plugins {
    id("diamond-kotlin-common")
}

// https://cucumber.io/docs/guides/10-minute-tutorial?lang=kotlin
dependencies {
    implementation(project(":app"))
    implementation(project(":view"))

    implementation(Deps.ktor.server.testHost)
    implementation(project(":shared:logging"))
    implementation(Deps.logging.kotlin)

    testImplementation("io.cucumber:cucumber-java:7.32.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.32.0")
    testImplementation("org.junit.platform:junit-platform-suite:6.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-property:5.9.1")
}
