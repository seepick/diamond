package nl.uwv.smz.diamond.shared.config

import com.sksamuel.hoplite.Secret
import com.sksamuel.hoplite.simpleName
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

// TODO move reporter to testFixtures; not polite the classpath; this is only used to generate a static report (MD file), not used at runtime!
private val log = logger {}

enum class ConfigType(val label: String) {
    String("string"), Integer("integer"), Boolean("boolean"),
}

data class ConfigEntry(
    val path: List<String>,
    val type: ConfigType,
    val description: String,
    val default: String? = null,
    // TODO val optional: Boolean,
) {
    init {
        require(description.isNotEmpty()) { "Description must not be empty for: ${path.joinToString(".")}" }
    }
}

fun parseConfigEntries(klass: KClass<*>): List<ConfigEntry> {
    log.debug { "Scanning: ${klass.qualifiedName}" }
    return scanForEntries(klass, emptyList(), klass.memberProperties)
}

private fun scanForEntries(
    klass: KClass<*>,
    pathPrefix: List<String>,
    properties: Collection<KProperty1<*, *>>,
): List<ConfigEntry> {
    log.debug { "Sub scanning. prefix: $pathPrefix; props: $properties" }
    return properties.filter { it.hasAnnotation<ConfigProperty>() }.map { prop ->
        @Suppress("UNCHECKED_CAST")
        ConfigEntry(
            path = pathPrefix + prop.name,
            type = prop.returnType.toConfigType(),
            description = prop.findAnnotation<ConfigProperty>()!!.description,
            default = reflectDefaultValue(klass, prop as KProperty1<Any, *>),
        )
    } + properties.filter { it.hasAnnotation<SubConfig>() }.map { prop ->
        val subKlass = prop.returnType.classifier as KClass<*>
        scanForEntries(subKlass, pathPrefix + prop.name, subKlass.memberProperties)
    }.flatten()
}

fun reflectDefaultValue(klass: KClass<*>, prop: KProperty1<Any, *>): String? {
    val cons = klass.primaryConstructor!!
    if (!cons.parameters.first { it.name == prop.name }.isOptional) {
        return null
    }
    val values = cons.parameters.filterNot { it.isOptional }.associateWith { constructAritificalDefaultValue(it) }
    val instance = cons.callBy(values)
    return prop.getValue(instance, prop).toString()
}

private fun constructAritificalDefaultValue(param: KParameter): Any = when (param.type.classifier) {
    String::class -> ""
    Int::class -> 0
    Boolean::class -> false
    Secret::class -> Secret("")
    else -> error("Unsupported type: ${param.type.classifier}")
}

fun KType.toConfigType(): ConfigType = when (simpleName) {
    "String" -> ConfigType.String
    "Int" -> ConfigType.Integer
    "Boolean" -> ConfigType.Boolean
    "class com.sksamuel.hoplite.Secret" -> ConfigType.String
    else -> error("Unhandled config property type: [${simpleName}]")
}
