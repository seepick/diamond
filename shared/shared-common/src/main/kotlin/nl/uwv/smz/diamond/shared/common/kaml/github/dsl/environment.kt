package nl.uwv.smz.diamond.shared.common.kaml.github.dsl

// should be declared outside (custom) by the project
enum class DemoEnvironmentNamespace(override val yamlValue: String) : Environment {
    Production("prod"),
    Acceptance("acc"),
    Test("test"),
    Development("dev"),
}

interface Environment {
    val yamlValue: String
}
