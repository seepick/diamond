description = "some description"

plugins {
    // TODO extract version
    id("io.ktor.plugin") version Versions.ktor apply false
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin apply false

}

allprojects {
    group = "nl.uwv.smz.diamond"
    version = "1-SNAPSHOT"
}
