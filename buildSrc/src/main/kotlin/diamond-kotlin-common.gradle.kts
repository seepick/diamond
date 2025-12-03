plugins {
    // declaring plugins not possible via version catalog
    // buildSrc/src/main/kotlin NOT available during runtime (misleading as available while writing)
    kotlin("jvm")
    id("diamond-versions")
    id("diamond-detekt")
    id("org.owasp.dependencycheck")
}

// TODO OWASP supports SARIF format; how to include in sonar? (or junitFailOnCVSS)
// ./gradlew dependencyCheckAnalyze
// suppressionFile

// no logging dependency, as API models don't need it (polluted!)

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict", // Annotations for Software Defect Detection
            "-opt-in=kotlin.uuid.ExperimentalUuidApi",
            "-opt-in=kotlin.uuid.ExperimentalTime",
        )
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versions.java)
    }
}

if (GradleProperty.enableOwasp.isSet() || GradleProperty.isCi.isSet()) {
    // TODO possible to move in here?
//    plugins {
//        id("org.owasp.dependencycheck")
//    }

    configure<org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension> {
        // Output directory default: build/reports/dependency-check
//    failBuildOnCVSS = 7f // score >= 7 is critical/high
        failBuildOnCVSS = 3f // medium+

        // formats: ALL (HTML, JSON, XML), HTML, JSON, XML, CSV
//    format = org.owasp.dependencycheck.reporting.ReportFormat.ALL
//    suppressionFile = rootProject.file("config/owasp-suppressions.xml")
//    scan { // Additional paths to scan (e.g., Dockerfiles, config files)
//        isFailOnError = true
//        path = fileTree(mapOf("dir" to "src", "include" to "**/*.jar"))
//    }
//    exclude = listOf("**/test/**", "**/*.txt")
    }

    tasks.named("check") {
        dependsOn("dependencyCheckAnalyze")
    }
}
