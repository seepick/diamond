plugins {
    id("io.ktor.plugin") version Versions.ktor apply false
    // TODO could we apply=false all other plugins here as well, and use Versions.plugins.*?!
    // instead of cumbersome impl dependency in buildSrc build file?! :)

//    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin apply false
    id("org.openapi.generator") version "7.0.1" apply false
}

val appVersion = GradleProperty.appVersion.get() ?: "SNAPSHOT"
gradleLog("appVersion passed via diamond_version property is: [$appVersion]")

allprojects {
    group = "nl.uwv.smz.diamond"
    version = appVersion
    description = "diamond sample project"
}
