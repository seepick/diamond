// https://github.com/karatelabs/karate/wiki/Gradle
plugins {
    id("diamond-kotlin-common")
//    id("diamond-kotlin-test") // FIXME this f*cks up the ability to run karate tests via junit?!?
}

// ./gradlew test -Dkarate.options="--tags @smoke"

dependencies {
    val karateVersion = "1.5.2"
    testImplementation("io.karatelabs:karate-junit5:$karateVersion")
    testImplementation(Deps.testing.junit.jupiter)
    testImplementation(Deps.testing.junit.platformSuite)

    // Optional but recommended: faster HTTP client
//    testImplementation("com.intuit.karate:karate-apache:$karateVersion")
}

sourceSets {
    named("test") {
        resources {
            srcDir("src/test/kotlin")
            exclude("**/*.kt")
        }
    }
}

tasks.withType<Test>().configureEach {
    if (GradleProperty.etests.isSet()) {
        gradleLog("Going to run the Karate E2E test suite.")
        useJUnitPlatform()
    } else {
        failOnNoDiscoveredTests.set(false)
    }
}
