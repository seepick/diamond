package nl.uwv.smz.diamond.shared.config

import com.sksamuel.hoplite.simpleName
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

// TODO move reporter to testFixtures; not polite the classpath; this is only used to generate a static report (MD file), not used at runtime!
private val log = logger {}

data class ConfigEntry(
    val path: List<String>,
    val type: String, // TODO could translate "Int->integer", "String->string", ...
    val description: String,
)

fun parseConfigEntries(klass: KClass<*>): List<ConfigEntry> {
    log.debug { "Scanning: ${klass.qualifiedName}" }
    return scanForEntries(emptyList(), klass.memberProperties)
}

private fun scanForEntries(pathPrefix: List<String>, properties: Collection<KProperty1<*, *>>): List<ConfigEntry> {
    log.debug { "Sub scanning. prefix: $pathPrefix; props: $properties" }
    return properties.filter { it.hasAnnotation<ConfigProperty>() }.map { prop ->
        ConfigEntry(
            path = pathPrefix + prop.name,
            type = prop.returnType.simpleName,
            description = prop.findAnnotation<ConfigProperty>()!!.description,
        )
    } + properties.filter { it.hasAnnotation<SubConfig>() }.map { prop ->
        val x = prop.returnType.classifier as KClass<*>
        scanForEntries(pathPrefix + prop.name, x.memberProperties)
    }.flatten()
}
