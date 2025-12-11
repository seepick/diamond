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
        maven {
            url = uri("https://maven.pkg.github.com/seepick/kaml")
            name = "KAML GitHubPackages"

            /*
            TODO enable auth during build
env:
  GITHUB_ACTOR: ${{ github.actor }}
  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
             */
//            credentials {
//                username = System.getenv("GITHUB_ACTOR")
//                    ?: error("GITHUB_ACTOR environment variable not set")
//
//                password = System.getenv("GITHUB_TOKEN")
//                    ?: error("GITHUB_TOKEN environment variable not set")
//            }
            credentials {
                username = project.findProperty("gpr.user") as? String ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as? String ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

dependencies {
    // TODO aggregated reports
//    kover(project(":view:view-routing"))
//    kover(project(":view:controller-impl"))
    implementation(project(":shared:kaml:kaml-github"))

    implementation("com.github.seepick.kaml:kaml-github:1.0-SNAPSHOT")
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
