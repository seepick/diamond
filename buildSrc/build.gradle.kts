plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    gradlePluginPortal()
}

dependencies {
    // PITY: reference to Versions.xxx not possible :-(
    // INFO: no version numbers for plugins in custom gradle-plugins; declare as dependency here instead

    val kotlinVersion = "2.3.0"
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion") // kotlin("jvm")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion") // kotlin("plugin.serialization")

    val versionKover = "0.9.3"
    implementation("org.jetbrains.kotlinx:kover-gradle-plugin:$versionKover")
    val manesVersion = "0.52.0"
    implementation("com.github.ben-manes.versions:com.github.ben-manes.versions.gradle.plugin:$manesVersion")

    // ATTENTION!!! duplicate version number in diamond-detekt.gradle.kts
    val detektVersion = "1.23.8"
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")

    // id("org.owasp.dependencycheck")
    val owaspVersion = "12.1.9"
    implementation("org.owasp.dependencycheck:org.owasp.dependencycheck.gradle.plugin:$owaspVersion")
//    implementation("???:org.jlleitschuh.gradle.ktlint:14.0.1")
}
