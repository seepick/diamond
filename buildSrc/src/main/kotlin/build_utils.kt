import org.gradle.api.Project

/** First -P then -D */
fun Project.lookupGradleProperty(property: Constants.GradleProperty, default: String? = null): String? =
    findProperty(property.value) as? String?
        ?: System.getProperty(property.value)
        ?: default

fun Project.hasGradleProperty(property: Constants.GradleProperty): Boolean =
    providers.gradleProperty(property.value).isPresent

fun enhanceSystemProperties(vararg more: Pair<String, String>): Map<String, Any> =
    enhanceSystemProperties(more.toList())

fun enhanceSystemProperties(more: List<Pair<String, String>>): Map<String, Any> =
    System.getProperties().asIterable().associate { it.key.toString() to it.value }.plus(more.toMap())

fun Project.gradleLog(message: String) {
    logger.info("[GRADLE:${fullProjectName()}] $message")
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

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}
