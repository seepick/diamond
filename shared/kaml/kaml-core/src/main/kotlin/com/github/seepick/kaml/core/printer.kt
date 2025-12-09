package com.github.seepick.kaml.core

// or use something like: https://github.com/charleskorn/kaml
// or: https://mojoauth.com/parse-and-generate-formats/parse-and-generate-yaml-with-kotlin/

private const val NAME_VAL_SEPARATOR = ":"

@Deprecated(message = "Use eo-yaml instead")
fun YamlTree.toYamlStringOld(): String {
    val yaml = StringBuilder()
    rootNodes.forEach { node ->
        buildYaml(yaml, node, 0, "")
    }
    return yaml.dropLastWhile { it == '\n' }.toString()
}

private fun buildYaml(yaml: StringBuilder, node: Node, indentLevel: Int, indentSuffix: String) {
    yaml.appendIndent(indentLevel).append(indentSuffix).append(node.name).append(NAME_VAL_SEPARATOR)
    when (node.value) {
        EmptyNodeValue -> EmptyNodeValue.prepare(yaml)
        is LeafNodeValue -> node.value.prepare(yaml)
        is ContainerNodeValue -> node.value.prepare(yaml, indentLevel, indentSuffix)
        is ScalarListNodeValue -> node.value.prepare(yaml, indentLevel)
        is ObjectListNodeValue -> node.value.prepare(yaml, indentLevel, indentSuffix)
        is PrefixedObjectListNodeValue -> node.value.prepare(yaml, indentLevel) // or pass indentSuffix?!
    }
}

private fun EmptyNodeValue.prepare(yaml: StringBuilder) {
    yaml.appendLine()
}

private fun LeafNodeValue.prepare(yaml: StringBuilder) {
    yaml.append(" ").append(leaf).appendLine()
}

private fun ContainerNodeValue.prepare(yaml: StringBuilder, indentLevel: Int, indentSuffix: String) {
    yaml.appendLine()
    buildYaml(yaml, subNode, indentLevel + 1, indentSuffix)
}

private fun ScalarListNodeValue.prepare(yaml: StringBuilder, indentLevel: Int) {
    yaml.appendLine()
    values.forEach { singleListValue ->
        yaml.appendIndent(indentLevel + 1).append("- ").append(singleListValue).appendLine()
    }
}

private fun ObjectListNodeValue.prepare(yaml: StringBuilder, indentLevel: Int, indentSuffix: String) {
    yaml.appendLine()
    nodes.forEach { subNode ->
        buildYaml(yaml, subNode, indentLevel + 1, indentSuffix)
    }
}

private fun PrefixedObjectListNodeValue.prepare(yaml: StringBuilder, indentLevel: Int) {
    yaml.appendLine()
    nodesNodes.forEach { nodes ->
        nodes.forEachIndexed { i, node ->
            buildYaml(yaml, node, indentLevel + 1, indentSuffix = if (i == 0) "- " else "  ")
        }
    }
}

private fun StringBuilder.appendIndent(level: Int) = apply {
    repeat(level) {
        append("  ")
    }
}
