import gradle.kotlin.dsl.accessors._dfefe04184237bb8e2cfe40aa2a2bf83.testImplementation

plugins {
    // gradle tasks: koverVerify and koverHtmlReport
    id("org.jetbrains.kotlinx.kover")
    // to merge reports from different projects: kover(project(":another:project"))
    // aggregated plugin is in early alpha development :-/
}

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
        events("failed", "skipped")
        showStandardStreams = false

        // TODO enable log output via gradle property
//        events("passed", "failed", "skipped", "standardOut", "standardError")
//        showStandardStreams = true
    }
}

// Kover produces a JaCoCo-compatible XML at `build/reports/kover/report.xml` by default.
kover {
    // automatically attached to check target
    // https://kotlin.github.io/kotlinx-kover/gradle-plugin/
    reports {
        verify {
            rule {
                // line coverage
                minBound(
                    when (project.name) {
                        // would be usually >80% ;)
                        "app" -> 1

                        else -> 3
                    },
                )
            }
        }
    }
}
