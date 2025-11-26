import org.gradle.api.Project

fun enhanceSystemProperties(vararg more: Pair<String, String>): Map<String, Any> =
    enhanceSystemProperties(more.toList())

fun enhanceSystemProperties(more: List<Pair<String, String>>): Map<String, Any> =
    System.getProperties().asIterable().associate { it.key.toString() to it.value }.plus(more.toMap())

fun Project.gradleLog(message: String) {
    println("[GRADLE:${fullProjectName()}] $message")
}

fun Project.fullProjectName(): String {
    var fullName = name
    var current = parent
    while (current != null) {
        fullName = "${current.name}:$fullName"
        current = current.parent
    }
    return fullName
}

fun Project.hasGradleProperty(property: Constants.GradleProperty): Boolean =
    providers.gradleProperty(property.value).isPresent
