import org.gradle.api.Project

val Project.diamondOpenApiPath: String get() = "${rootDir.absolutePath}/config/openapi.yml"
