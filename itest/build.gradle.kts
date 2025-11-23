plugins {
    id("diamond-kotlin-common")
}

// https://cucumber.io/docs/guides/10-minute-tutorial?lang=kotlin
dependencies {
    val versionCucumber = "7.32.0"
    implementation(project(":app"))
    implementation(project(":view"))
    implementation(project(":client-sdk"))
    implementation(project(":shared:shared-commons"))
    implementation(project(":shared:shared-logging"))
    // https://cucumber.io/docs/cucumber/state/#picocontainer
    // https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-picocontainer
    implementation("io.cucumber:cucumber-picocontainer:$versionCucumber")

    implementation(Deps.ktor.server.testHost)
    implementation(Deps.logging.kotlin)

    testImplementation("io.cucumber:cucumber-java:$versionCucumber")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:$versionCucumber")
    testImplementation("org.junit.platform:junit-platform-suite:6.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation(Deps.testing.kotest.assertions)
//    testImplementation(Deps.testing.kotest.property)
}

tasks.withType<Test> {
//// Parallel forks across JVMs
//    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(2)
//    forkEvery = 1
//
//// Useful output
//    testLogging {
//        events("passed", "failed", "skipped", "standardOut", "standardError")
//        showStandardStreams = true
//    }
}
