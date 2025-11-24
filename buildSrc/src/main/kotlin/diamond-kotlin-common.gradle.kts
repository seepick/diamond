plugins {
    // no version numbers; declare as dependency in buildSrc/build.gradle.kts
    kotlin("jvm")
    id("diamond-versions")
    // declaring plugins not possible via version catalog
    // buildSrc/src/main/kotlin NOT available during runtime (misleading as available while writing)
}

repositories {
    mavenCentral()
}

// no logging dependency, as API models don't need it (polluted!)

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict", // Annotations for Software Defect Detection
            "-opt-in=kotlin.uuid.ExperimentalUuidApi",
        )
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versions.java)
    }
}
