package com.github.seepick.kaml.github.yaml

import com.github.seepick.kaml.core.LeafNodeValue
import com.github.seepick.kaml.core.Node
import com.github.seepick.kaml.core.ObjectListNodeValue
import com.github.seepick.kaml.core.PrefixedObjectListNodeValue
import com.github.seepick.kaml.github.domain.GenericStep
import com.github.seepick.kaml.github.domain.GithubAction
import com.github.seepick.kaml.github.domain.Job
import com.github.seepick.kaml.github.domain.RunStep
import com.github.seepick.kaml.github.domain.Step

internal fun GithubAction.buildJobs(): Node? {
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
