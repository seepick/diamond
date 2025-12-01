plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
    kotlin("plugin.serialization")
}

// https://cucumber.io/docs/guides/10-minute-tutorial?lang=kotlin
dependencies {
    implementation(project(":app"))
    implementation(project(":persistence:persistence-impl"))
    implementation(project(":client-sdk"))
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-logging"))
    // https://cucumber.io/docs/cucumber/state/#picocontainer
    // https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-picocontainer
    implementation(Deps.testing.cucumber.picocontainer)
    implementation(Deps.ktor.server.testHost)
    implementation(Deps.logging.kotlin)

    testImplementation(project(":view:view-model")) // FIXME remove and generate DTOs based on OpenAPI spec
    testImplementation(project(":shared:shared-test"))
    testImplementation(project(":shared:shared-testKtor"))
    testImplementation(project(":extern:extern-impl")) // TODO to only get the config... part of *-api?!?
    testImplementation(project(":extern:extern-stub"))
    testImplementation(Deps.ktor.client.contentNegotiation)
    testImplementation(Deps.ktor.client.logging)
    testImplementation(Deps.ktor.serialization)
    testImplementation(Deps.koin.ktor)
    testImplementation(Deps.testing.cucumber.java) // needed as some cucumber features are not working with java8
    testImplementation(Deps.testing.cucumber.java8) // lambda definition
    testImplementation(Deps.testing.cucumber.junitEngine)
    testImplementation(Deps.testing.junit.platformSuite)
    testImplementation(Deps.testing.junit.jupiter)
    testImplementation(Deps.testing.jsonPath)
    testImplementation(Deps.testing.jsonPathHamcrestAssert)
    testImplementation(Deps.testing.jsonAssert)
    testImplementation(Deps.testing.hamcrest)
    // see: https://dzone.com/articles/automating-cucumber-data-table-to-java-object-mapping
    testImplementation("io.github.deblockt:cucumber-datatable-to-bean-mapping:1.1.2")
}

// tasks.withType<Test> {
// // Parallel forks across JVMs
//    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(2)
//    forkEvery = 1
//
// // Useful output
//    testLogging {
//        events("passed", "failed", "skipped", "standardOut", "standardError")
//        showStandardStreams = true
//    }
// }
