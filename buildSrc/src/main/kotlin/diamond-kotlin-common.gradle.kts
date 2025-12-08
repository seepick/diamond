plugins {
    // declaring plugins not possible via version catalog
    // buildSrc/src/main/kotlin NOT available during runtime (misleading as available while writing)
    kotlin("jvm")
    id("diamond-versions")
    id("diamond-detekt")
    id("org.owasp.dependencycheck")
}

dependencies {
    // enforce certain versions to avoid OWASP vulnerabilities in a central place
    implementation(platform(project(":shared:gradle-platform")))
}

// TODO OWASP supports SARIF format; how to include in sonar? (or junitFailOnCVSS)
// ./gradlew dependencyCheckAnalyze
// suppressionFile

// no logging dependency, as API models don't need it (polluted!)

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict", // annotations for defect detection
            "-opt-in=kotlin.uuid.ExperimentalUuidApi",
        )
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versions.java)
    }
}

if (GradleProperty.enableOwasp.isSet()) {
    // TODO possible to move in here?
//    plugins {
//        id("org.owasp.dependencycheck")
//    }

    configure<org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension> {
        // Output directory default: build/reports/dependency-check

//    failBuildOnCVSS = 7f // score >= 7 is critical/high
        failBuildOnCVSS = 3f // medium+

        suppressionFile.set("$rootDir/config/owasp-suppression.xml")
        analyzers {
            val ossUser = GradleProperty.owaspOssUsername.get()
            val ossPass = GradleProperty.owaspOssPassword.get()
            if (ossUser != null && ossPass != null) {
                ossIndex {
                    // requires login to work propelry
                    gradleLog("Declaring credentials for OWASP OSS Index.")
                    username.set(ossUser)
                    password.set(ossPass)
                }
            }
            // or: -Ddependency-check.analyzer.assembly.enabled=false
            assemblyEnabled = false // no EXE, DLL things; we are not .net
        }

        // make use of suppression file (if needed)

        // formats: ALL (HTML, JSON, XML), HTML, JSON, XML, CSV
//    format = org.owasp.dependencycheck.reporting.ReportFormat.ALL
//    suppressionFile = rootProject.file("config/owasp-suppressions.xml")
//    scan { // Additional paths to scan (e.g., Dockerfiles, config files)
//        isFailOnError = true
//        path = fileTree(mapOf("dir" to "src", "include" to "**/*.jar"))
//    }
//    exclude = listOf("**/test/**", "**/*.txt")
    }

    // it's better to register tasks to lifecycle, than to provide additional tasks
    // (applied IoC, more flexible, less error-prone)
    tasks.named("check") {
        dependsOn("dependencyCheckAnalyze")
    }
}
