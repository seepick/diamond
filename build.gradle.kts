plugins {
    id("io.ktor.plugin") version Versions.ktor apply false
//    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin apply false
}

val appVersion = project.properties["diamond_version"]?.toString() ?: "0"
println("[DIAMOND] Gradle appVersion=[$appVersion]")

allprojects {
    group = "nl.uwv.smz.diamond"
    version = appVersion
    description = "diamond sample project"
}

// $ gradle performRelease -PisCI=true --quiet
tasks.register("performRelease") {
    val isCI = providers.gradleProperty("isCI")
    doLast {
        if (isCI.isPresent) {
            println("Performing release actions")
        } else {
            throw InvalidUserDataException("Cannot perform release outside of CI")
        }
    }
}
