package nl.uwv.smz.diamond.shared.common.kaml

// TODO more KAML features/ideas
// * provide nice DSL to construct yamls
// * extensibility: allow for totally custom steps
// * and now showcase how to build layer on top of DSL (reuse, reference)
// * would be nice to support injecting comments anywhere ;)
// * multiline run
// steps:
//  - name: Execute script
//    run: |
//      chmod +x ./script.sh
//      ./script.sh

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
object EmptyNodeValue : NodeValue {
    fun bar() {}
}

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
