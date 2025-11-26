plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    // PITY: reference to Versions.xxx not possible :-(
    // INFO: no version numbers for plugins in custom gradle-plugins; declare as dependency here instead

    val kotlinVersion = "2.2.21"
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion") // kotlin("jvm")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion") // kotlin("plugin.serialization")

    implementation("com.github.ben-manes.versions:com.github.ben-manes.versions.gradle.plugin:0.52.0")
    // ATTENTION!!! duplicate version number in diamond-detekt.gradle.kts
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")

}
