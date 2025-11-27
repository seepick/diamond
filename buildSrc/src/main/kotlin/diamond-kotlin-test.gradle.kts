import gradle.kotlin.dsl.accessors._fb21cc0d1272f53256e1229e5b966fd1.testImplementation

dependencies {
    testImplementation(project(":shared:shared-test"))
    testImplementation(Deps.testing.kotest.junitRunner)
    testImplementation(Deps.testing.kotest.assertions)
    testImplementation(Deps.testing.kotest.assertionsArrow)
    testImplementation(Deps.testing.kotest.property)
    testImplementation(Deps.testing.junit.platformSuite)
    testImplementation(Deps.testing.junit.jupiter)
    testImplementation(Deps.testing.mockk)
}

tasks.withType<Test>().configureEach {
    // to be able to run kotests
    useJUnitPlatform()
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(2)
    forkEvery = 1

    systemProperties = enhanceSystemProperties(
        "kotest.framework.config.fqn" to Constants.Fqn.kotestProjectConfig,
        // get rid of warning "Kotest autoscan is enabled" when running tests
        "kotest.framework.classpath.scanning.config.disable" to "true",
    )

    testLogging {
//        events("passed", "failed", "skipped")
//        showStandardStreams = false

        // TODO enable log output via gradle property
        events("passed", "failed", "skipped", "standardOut", "standardError")
        showStandardStreams = true
    }
}
