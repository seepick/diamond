package nl.uwv.smz.diamond.shared.common.kaml.github.yaml

import nl.uwv.smz.diamond.shared.common.kaml.LeafNodeValue
import nl.uwv.smz.diamond.shared.common.kaml.Node
import nl.uwv.smz.diamond.shared.common.kaml.YamlTree
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.GithubAction
import nl.uwv.smz.diamond.shared.common.kaml.toYamlString

fun GithubAction.toYamlTree() =
    YamlTree(
        rootNodes = buildList {
            add(Node("name", LeafNodeValue(name)))
            buildTriggers()?.also { add(it) }
            buildJobs()?.also { add(it) }
        },
    )

fun GithubAction.toYamlString() =
    toYamlTree().toYamlString()
