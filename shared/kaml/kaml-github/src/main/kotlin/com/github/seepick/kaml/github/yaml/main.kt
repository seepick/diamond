package com.github.seepick.kaml.github.yaml

import com.github.seepick.kaml.core.LeafNodeValue
import com.github.seepick.kaml.core.Node
import com.github.seepick.kaml.core.YamlTree
import com.github.seepick.kaml.core.toYamlString
import com.github.seepick.kaml.github.domain.GithubAction

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
