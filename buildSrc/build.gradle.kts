// see: https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

//id("io.ktor.plugin") version "3.3.2"
//id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
// TODO reference to Versions.xxx not possible
dependencies {
    val kotlinVersion = "2.2.21"
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion") // kotlin("jvm")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion") // kotlin("plugin.serialization")

    implementation("com.github.ben-manes.versions:com.github.ben-manes.versions.gradle.plugin:0.52.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8") // duplicate version number in diamond-detekt.gradle.kts

}
