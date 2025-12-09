package com.github.seepick.kaml.github.yaml

import com.github.seepick.kaml.core.ContainerNodeValue
import com.github.seepick.kaml.core.EmptyNodeValue
import com.github.seepick.kaml.core.LeafNodeValue
import com.github.seepick.kaml.core.Node
import com.github.seepick.kaml.core.ObjectListNodeValue
import com.github.seepick.kaml.core.PrefixedObjectListNodeValue
import com.github.seepick.kaml.core.ScalarListNodeValue
import com.github.seepick.kaml.github.domain.CronTrigger
import com.github.seepick.kaml.github.domain.GithubAction
import com.github.seepick.kaml.github.domain.ManualTrigger
import com.github.seepick.kaml.github.domain.OnPushBranchTrigger

internal fun GithubAction.buildTriggers(): Node? {
    if (triggers.isEmpty()) {
        return null
    }

    return Node(
        "on",
        ObjectListNodeValue(
            triggers.map { trigger ->
                when (trigger) {
                    is OnPushBranchTrigger -> {
                        Node(
                            "push",
                            ContainerNodeValue(
                                Node("branches", ScalarListNodeValue(trigger.branchNames)),
                            ),
                        )
                    }

                    is CronTrigger -> {
                        Node(
                            "schedule",
                            PrefixedObjectListNodeValue(
                                listOf(
                                    listOf(
                                        Node(
                                            "cron",
                                            LeafNodeValue(trigger.pattern),
                                        ),
                                    ),
                                ),
                            ),
                        )
                    }

                    ManualTrigger -> {
                        Node("workflow_dispatch", EmptyNodeValue)
                    }
                }
            },
        ),
    )
}
