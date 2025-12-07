import org.apache.tools.ant.filters.ReplaceTokens
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
    id("io.ktor.plugin")
    application
}

ktor {
    docker {
        localImageName.set("diamond")
        imageTag.set("latest")
        jreVersion.set(JavaVersion.VERSION_17)
        // NO, this needs to be done by the "host"; by default mapping 8080:8080
//        portMappings.set(listOf(
//            io.ktor.plugin.features.DockerPortMapping(
//                outsideDockerPort,
//                insideDockerPort,
//                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
//            )
//        ))
//        externalRegistry.set(
//            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
//                appName = provider { "ktor-app" },
//                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
//                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
//            )
//        )
    }
}

dependencies {
    api(project(":view:view-routing"))
    implementation(project(":domain:domain-logic-impl"))
    implementation(project(":view:controller-impl"))
    implementation(project(":extern:extern-impl"))
    implementation(project(":persistence:persistence-impl"))
    implementation(project(":shared:shared-logging"))
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-config"))

    implementation(Deps.logging.kotlin)
    implementation(Deps.ktor.server.core)
    implementation(Deps.ktor.server.netty)
    implementation(Deps.koin.ktor)
    implementation(Deps.koin.logger)

    testImplementation(project(":extern:extern-stub"))
    testImplementation(Deps.koin.test)
}

application {
    mainClass = Constants.Fqn.mainClass
}

ktor {
    fatJar {
        archiveFileName.set(Constants.assemblyName)
    }
}

fun registerJavaExecTask(name: String, group: String, description: String, mainClass: String) {
    tasks.register<JavaExec>(name) {
        // instead "runBoot" ;)
        this.group = group
        this.description = description
        // TODO make output configurable
        this.mainClass.set(mainClass)
        workingDir = rootDir
        classpath = java.sourceSets["test"].runtimeClasspath
    }
}

registerJavaExecTask(
    name = "runLocal",
    group = "application",
    description = "asdf",
    mainClass = Constants.Fqn.localMainClass,
)

/** generate asciidoc describing the support environment variables to be set by operations */
registerJavaExecTask(
    // TODO make output configurable
    name = "generateConfigDoc", // instead "runBoot" ;)
    group = "documentation",
    description = "Generate ENV vars overview by using reflection.",
    mainClass = Constants.Fqn.configDocWriter,
)

configure<ProcessResources>("processResources") {
    from("src/main/resources") {
        include("buildInjected.properties")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filter<ReplaceTokens>(
            "tokens" to mapOf(
                "appVersion" to version,
                "buildTime" to LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "branchName" to (GradleProperty.branchName.get() ?: "?BRANCH?"),
            ),
        )
    }
}
