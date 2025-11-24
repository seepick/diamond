import io.gitlab.arturbosch.detekt.Detekt

// https://detekt.dev/
// gradle check implies detekt
// see result: build/reports/detekt/detekt

// detektMain and detektTest to enable type resolution (by default off)
// better to create custom task!

// intellij plugin: warning highlight directly inside the IDE as well as support for code formatting
// Refactor -> AutoCorrect by Detekt rules

// gradle.properties ... detekt.use.worker.api = true
// for KMP (client-SDK): detektMetadataCommonMain

// and because gradle build files are also just kotlin, in intellij, the detekt plugin will also check those -sweet ;)
plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    // When unspecified the latest detekt version found will be used. Override to stay on the same version.
    toolVersion = "1.23.8" // duplicate version number in /buildSrc/build.gradle.kts
    source.setFrom("src/main/kotlin", "src/test/kotlin", "src/testFixtures/kotlin")
    // Builds the AST in parallel. Rules are always executed in parallel. `false` by default.
    parallel = true
    config.setFrom(project.rootDir.absolutePath + "/config/detekt.yml")
//    debug = true

//    // The build fails when there is at least one issue with this severity (or above).
//    // If set ot `Never`, the task will not fail regardless of the number of issues and their severities.
//    // If `ignoreFailures` is set to `true`, the value of this property is ignored.
//    // Defaults to `Error`
//    failOnSeverity = dev.detekt.gradle.extensions.FailOnSeverity.Error
//
//    // Specify the base path for file paths in the formatted reports.
//    // If not set, all file paths reported will be absolute file path.
//    basePath.set(projectDir)
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        sarif.required.set(false)
        md.required.set(false)
        txt.required.set(false)
        xml.required.set(false)
        // starting with v2.0.0
//        checkstyle.required.set(true)
    }
}

// required for type resolution
// tasks.withType<dev.detekt.gradle.Detekt>().configureEach {
//    jvmTarget.set("1.8")
//    jdkHome.set(file("path/to/jdkHome"))
// }
// tasks.withType<dev.detekt.gradle.DetektCreateBaselineTask>().configureEach {
//    jvmTarget.set("1.8")
//    jdkHome.set(file("path/to/jdkHome"))
// }

// tasks.withType<dev.detekt.gradle.Detekt>().configureEach {
//    // include("**/special/package/**") // only analyze a sub package inside src/main/kotlin
//    exclude("**/special/package/internal/**") // but exclude our legacy internal package
// }
//
// tasks.withType<dev.detekt.gradle.DetektCreateBaselineTask>().configureEach {
//    // include("**/special/package/**") // only analyze a sub package inside src/main/kotlin
//    exclude("**/special/package/internal/**") // but exclude our legacy internal package
// }
