import gradle.kotlin.dsl.accessors._662d112ba7efebaa06e1f8125d03b535.sourceSets
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

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

data class JavaExecConfig(
    val name: String,
    val group: String,
    val description: String,
    val mainClass: String,
    val args: List<String> = emptyList(),
)

fun Project.registerJavaExecTask(config: JavaExecConfig) {
    tasks.register<JavaExec>(config.name) {
        group = config.group
        description = config.description
        mainClass.set(config.mainClass)
        args = config.args
        workingDir = rootDir
        classpath = sourceSets["test"].runtimeClasspath
    }
}
