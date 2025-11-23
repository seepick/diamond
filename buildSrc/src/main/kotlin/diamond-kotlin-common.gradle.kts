import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    // no version numbers; declare as dependency in buildSrc/build.gradle.kts
    kotlin("jvm")
    // declaring plugins not possible via version catalog
    // buildSrc/src/main/kotlin NOT available during runtime (misleading as available while writing)
    id("com.github.ben-manes.versions") // help / dependencyUpdates
}

repositories {
    mavenCentral()
}

// no logging dependency, as API models don't need it (polluted!)

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versions.java)
    }
}

tasks.withType<Test>().configureEach { // to be able to run kotests
    useJUnitPlatform()
}

tasks.withType<DependencyUpdatesTask> {
    val rejectPatterns =
        listOf(".*-ea.*", ".*RC", ".*M1", ".*check", ".*dev.*", ".*[Bb]eta.*", ".*[Aa]lpha.*").map { Regex(it) }
    rejectVersionIf {
        rejectPatterns.any {
            it.matches(candidate.version)
        }
    }
}
