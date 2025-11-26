package nl.uwv.smz.diamond.shared.config

@Target(AnnotationTarget.PROPERTY)
annotation class ConfigProperty(val description: String)

@Target(AnnotationTarget.PROPERTY)
annotation class SubConfig

// TODO Optional (nullable) property values?!
