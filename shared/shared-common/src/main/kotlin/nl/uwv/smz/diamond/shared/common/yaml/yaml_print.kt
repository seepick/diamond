package nl.uwv.smz.diamond.shared.common.yaml

// or use something like: https://github.com/charleskorn/kaml
// or: https://mojoauth.com/parse-and-generate-formats/parse-and-generate-yaml-with-kotlin/

private const val NAME_VAL_SEPARATOR = ":"

fun YamlTree.toYamlString(): String {
    val yaml = StringBuilder()
    rootNodes.forEach { node ->
        yaml.append(buildYaml(node, 0, ""))
    }
    return yaml.toString()
}

private fun buildYaml(node: Node, indentLevel: Int, indentSuffix: String): String {
    val yaml = StringBuilder()
    yaml.appendIndent(indentLevel).append(indentSuffix).append(node.name).append(NAME_VAL_SEPARATOR)
    when (node.value) {
        EmptyNodeValue -> {
            yaml.appendLine()
        }

        is LeafNodeValue -> {
            yaml.append(" ").append(node.value.value).appendLine()
        }

        is ContainerNodeValue -> {
            yaml.appendLine().append(buildYaml(node.value.subNode, indentLevel + 1, indentSuffix))
        }

        is ScalarListNodeValue -> {
            yaml.appendLine()
            node.value.values.forEach { singleListValue ->
                yaml.appendIndent(indentLevel + 1).append("- ").append(singleListValue).appendLine()
            }
        }

        is ObjectListNodeValue -> {
            yaml.appendLine()
            node.value.nodes.forEach { subNode ->
                yaml.append(buildYaml(subNode, indentLevel + 1, indentSuffix))
            }
        }

        is PrefixedObjectListNodeValue -> {
            yaml.appendLine()
            node.value.nodesNodes.forEach { nodes ->
                nodes.forEachIndexed { i, node ->
                    yaml.append(buildYaml(node, indentLevel + 1, indentSuffix = if (i == 0) "- " else "  "))
                }
            }
        }
    }
    return yaml.toString()
}

private fun StringBuilder.appendIndent(level: Int) = apply {
    repeat(level) {
        append("  ")
    }
}
