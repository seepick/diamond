import io.gitlab.arturbosch.detekt.Detekt

// gradle check implies detekt
// run detektMain/detektTest to enable type resolution (by default off)
// => better to create custom task!
// IntelliJ: Refactor -> AutoCorrect by Detekt rules

plugins {
    id("io.gitlab.arturbosch.detekt")
    // https://github.com/JLLeitschuh/ktlint-gradle
//    id("org.jlleitschuh.gradle.ktlint")
    // ./gradlew ktlintCheck
}

// configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
//    debug.set(true)
// }
//
// ktlint {
// version.set("0.41.0-SNAPSHOT")
//    android = false
// verbose.set(true)
// android.set(false)
// outputToConsole.set(true)
// outputColorName.set("RED")
// ignoreFailures.set(false)
//    ignoreFailures = false
//    colored output = ...
//    reporters {
//        reporter(ReporterType.PLAIN)
//        reporter(ReporterType.CHECKSTYLE)
//        reporter(ReporterType.SARIF)
//    }
// }

val detektVersion = "1.23.8"
// val detektVersion = "2.0.0-alpha.1" // see also buildSrc/build.gradle.kts

dependencies {
    // more plugins here: https://detekt.dev/marketplace/
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    // https://detekt.dev/docs/next/rules/ktlint/
    detektPlugins("dev.detekt:detekt-rules-ktlint-wrapper:2.0.0-alpha.1")

//    ktlintRuleset("com.github.username:rulseset:main-SNAPSHOT")
//    ktlintRuleset(files("/path/to/custom/rulseset.jar"))
//    ktlintRuleset(project(":chore:project-ruleset"))
}

detekt {
    // When unspecified the latest detekt version found will be used. Override to stay on the same version.
    // ATTENTION!!! duplicate version number in /buildSrc/build.gradle.kts
    toolVersion = detektVersion

    source.setFrom("src/main/kotlin", "src/test/kotlin", "src/testFixtures/kotlin")
    parallel = true
    config.setFrom(project.rootDir.absolutePath + "/config/detekt.yml")
//    debug = true

    ignoreFailures = false
//    failOnSeverity = dev.detekt.gradle.extensions.FailOnSeverity.Error

    // Specify the base path for file paths in the formatted reports.
    // If not set, all file paths reported will be absolute file path.
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
        // checkstyle.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    // TODO set JDK for type resolution to b enabled (more rules)

//    jvmTarget.set("1.8")
//    jvmTarget = "1.8"
//    jdkHome.set(file("path/to/jdkHome"))
//    // include("**/special/package/**") // only analyze a sub package inside src/main/kotlin
//    exclude("**/special/package/internal/**") // but exclude our legacy internal package
}
