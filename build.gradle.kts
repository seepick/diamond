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

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/seepick/kaml")
        name = "KAML GitHubPackages"
        credentials {
            val (user, pass) = readGithubCredentials()
            username = user
            password = pass
        }
    }
}

dependencies {
    // TODO aggregated reports
//    kover(project(":view:view-routing"))
//    kover(project(":view:controller-impl"))

    implementation("com.github.seepick.kaml:kaml-github:1.0-SNAPSHOT")
}

registerJavaExecTask(
    JavaExecConfig(
        name = "generateKaml",
        group = "generation",
        description = "Generate Yaml based on Kaml",
        mainClass = Constants.Fqn.kamlGenerator,
        args = listOf(rootDir.absolutePath),
    ),
)
