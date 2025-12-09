package com.github.seepick.kaml.core

data class YamlTree(
    val rootNodes: List<Node>,
)

data class Node(
    val name: String,
    val value: NodeValue,
)

sealed interface NodeValue

/**
 * workflow_dispatch:
 */
object EmptyNodeValue : NodeValue

/**
 * name: Build Gradle
 */
data class LeafNodeValue(
    val leaf: String,
) : NodeValue

/**
 * super:
 *   sub: 42
 */
data class ContainerNodeValue(
    val subNode: Node,
) : NodeValue

/**
 * branches:
 * - foo
 * - bar
 */
data class ScalarListNodeValue(
    val values: List<String>,
) : NodeValue

data class ObjectListNodeValue(
    val nodes: List<Node>,
) : NodeValue

/** With a "-" prefix for each entry. */
data class PrefixedObjectListNodeValue(
    val nodesNodes: List<List<Node>>,
) : NodeValue
