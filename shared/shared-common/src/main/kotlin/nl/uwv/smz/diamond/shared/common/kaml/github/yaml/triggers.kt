package nl.uwv.smz.diamond.shared.common.kaml.github.yaml

import nl.uwv.smz.diamond.shared.common.kaml.ContainerNodeValue
import nl.uwv.smz.diamond.shared.common.kaml.EmptyNodeValue
import nl.uwv.smz.diamond.shared.common.kaml.LeafNodeValue
import nl.uwv.smz.diamond.shared.common.kaml.Node
import nl.uwv.smz.diamond.shared.common.kaml.ObjectListNodeValue
import nl.uwv.smz.diamond.shared.common.kaml.PrefixedObjectListNodeValue
import nl.uwv.smz.diamond.shared.common.kaml.ScalarListNodeValue
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.CronTrigger
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.GithubAction
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.ManualTrigger
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.OnPushBranchTrigger

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
