package nl.uwv.smz.diamond.shared.common.yaml.github

import nl.uwv.smz.diamond.shared.common.yaml.ContainerNodeValue
import nl.uwv.smz.diamond.shared.common.yaml.EmptyNodeValue
import nl.uwv.smz.diamond.shared.common.yaml.LeafNodeValue
import nl.uwv.smz.diamond.shared.common.yaml.Node
import nl.uwv.smz.diamond.shared.common.yaml.ObjectListNodeValue
import nl.uwv.smz.diamond.shared.common.yaml.PrefixedObjectListNodeValue
import nl.uwv.smz.diamond.shared.common.yaml.ScalarListNodeValue
import nl.uwv.smz.diamond.shared.common.yaml.YamlTree

fun GithubAction.toYamlTree(): YamlTree = YamlTree(
    rootNodes = computeRootNodes(),
)

// TODO provide nice DSL to construct yamls
private fun GithubAction.computeRootNodes(): List<Node> =
    buildList {
        add(Node("name", LeafNodeValue(name)))
        buildTriggers()?.also { add(it) }
        buildJobs()?.also { add(it) }
    }

private fun GithubAction.buildTriggers(): Node? {
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

private fun GithubAction.buildJobs(): Node? {
    if (jobs.isEmpty()) {
        return null
    }
    return Node(
        "jobs",
        ObjectListNodeValue(
            jobs.map { buildJob(it) },
        ),
    )
}

private fun GithubAction.buildJob(job: Job): Node = Node(
    job.id,
    ObjectListNodeValue(
        buildList {
            add(Node("name", LeafNodeValue(job.name)))
            add(Node("runs-on", LeafNodeValue(job.runsOn.image.coordinates)))
            job.environment?.let { environment ->
                add(Node("environment", LeafNodeValue(environment.yamlValue)))
            }
            if (job.permissions.isNotEmpty()) {
                add(
                    Node(
                        "permissions",
                        ObjectListNodeValue(
                            job.permissions.map { permission ->
                                Node(permission.type.yamlValue, LeafNodeValue(permission.level.yamlValue))
                            },
                        ),
                    ),
                )
            }
            add(
                Node(
                    "steps",
                    PrefixedObjectListNodeValue(
                        job.steps.map { step ->
                            buildJobStep(step)
                        },
                    ),
                ),
            )
        },
    ),
)

private fun GithubAction.buildJobStep(step: Step): List<Node> = buildList {
    add(Node("name", LeafNodeValue(step.name)))
    step.uses?.also { uses ->
        add(Node("uses", LeafNodeValue(uses.coordinates)))
    }
    when (step) {
        is GenericStep -> {
            if (step.withParams.isNotEmpty()) {
                add(
                    Node(
                        "with",
                        ObjectListNodeValue(
                            step.withParams.map { param ->
                                Node(param.key, LeafNodeValue(param.value))
                            },
                        ),
                    ),
                )
            }
        }

        is RunStep -> {
            // TODO multi line param if step.command contains linebreaks
            add(Node("run", LeafNodeValue(step.command)))
        }
    }
}
