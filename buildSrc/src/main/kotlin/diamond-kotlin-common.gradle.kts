import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import gradle.kotlin.dsl.accessors._e0089add3ded35fdd654c9963fdc04f9.java

plugins {
    // no version numbers; declare as dependency in buildSrc/build.gradle.kts
    kotlin("jvm")
    // not via version catalog possible :-/
    id("com.github.ben-manes.versions") // help / dependencyUpdates
}

repositories {
    mavenCentral()
}

kotlin { compilerOptions {
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
