package nl.uwv.smz.diamond.shared.common.yaml

sealed interface NodeValue

object EmptyNodeValue : NodeValue

data class LeafNodeValue(
    val value: String,
) : NodeValue

data class ObjectListNodeValue(
    val nodes: List<Node>,
) : NodeValue

/** With a "-" prefix for each entry. */
data class PrefixedObjectListNodeValue(
    val nodesNodes: List<List<Node>>,
) : NodeValue

data class ScalarListNodeValue(
    val values: List<String>,
) : NodeValue

data class ContainerNodeValue(
    val subNode: Node,
) : NodeValue

data class Node(
    val name: String,
    val value: NodeValue,
)

data class YamlTree(
    val rootNodes: List<Node>,
)
