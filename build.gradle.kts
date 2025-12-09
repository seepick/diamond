plugins {
    id("diamond-kotlin-common") // for util generators only

    id("io.ktor.plugin") version Versions.ktor apply false
    // TODO could we apply=false all other plugins here as well, and use Versions.plugins.*?!
    // instead of cumbersome impl dependency in buildSrc build file?! :)

//    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin apply false
    id("org.openapi.generator") version Versions.openapi apply false
    id("org.jetbrains.kotlinx.kover")
}

val appVersion = GradleProperty.appVersion.get() ?: "SNAPSHOT"
gradleLog("appVersion passed via diamond_version property is: [$appVersion]")

allprojects {
    group = "nl.uwv.smz.diamond"
    version = appVersion
    description = "diamond sample project"

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

dependencies {
    // TODO aggregated reports
//    kover(project(":view:view-routing"))
//    kover(project(":view:controller-impl"))
    implementation(project(":shared:kaml:kaml-github"))
}

registerJavaExecTask(
    JavaExecConfig(
        name = "generateKaml",
        group = "generation",
        description = "Generate YAML based on KAML",
        mainClass = Constants.Fqn.kamlGenerator,
        args = listOf(rootDir.absolutePath),
    ),
)
