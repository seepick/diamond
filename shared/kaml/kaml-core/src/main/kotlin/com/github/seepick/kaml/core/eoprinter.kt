package com.github.seepick.kaml.core

import com.amihaiemil.eoyaml.Yaml

fun main() {
    val tags = Yaml.createYamlSequenceBuilder()
        .add("admin")
        .add("user")
        .build()

    val mapping = Yaml.createYamlMappingBuilder()
        .add("name", "Alice")
        .add("age", 30)
        .add("tags", tags)
        .build()

    val yaml: String = mapping.toString()
    println(yaml)
}

fun YamlTree.toYamlString(): String = ""
