plugins {
    id("io.ktor.plugin") version Versions.ktor apply false
    // TODO could we apply=false manesVersion here, and use Versions.plugins.manes?!
//    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin apply false
    id("org.openapi.generator") version "7.0.1" apply false
}

val appVersion = project.properties["diamond_version"]?.toString() ?: "0"
gradleLog("appVersion=[$appVersion]")

allprojects {
    group = "nl.uwv.smz.diamond"
    version = appVersion
    description = "diamond sample project"
}
