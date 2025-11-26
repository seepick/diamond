plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

// https://cucumber.io/docs/guides/10-minute-tutorial?lang=kotlin
dependencies {
    implementation(project(":app"))
    implementation(project(":view"))
    implementation(project(":persistence:persistence-impl"))
    implementation(project(":client-sdk"))
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-logging"))
    // https://cucumber.io/docs/cucumber/state/#picocontainer
    // https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-picocontainer
    implementation(Deps.testing.cucumber.picocontainer)
    implementation(Deps.ktor.server.testHost)
    implementation(Deps.logging.kotlin)

    testImplementation(project(":shared:shared-test"))
    testImplementation(Deps.testing.cucumber.java)
    testImplementation(Deps.testing.cucumber.junitEngine)
    testImplementation(Deps.testing.junit.platformSuite)
    testImplementation(Deps.testing.junit.jupiter)
}

//tasks.withType<Test> {
//// Parallel forks across JVMs
//    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(2)
//    forkEvery = 1
//
//// Useful output
//    testLogging {
//        events("passed", "failed", "skipped", "standardOut", "standardError")
//        showStandardStreams = true
//    }
//}
