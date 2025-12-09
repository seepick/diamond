package com.github.seepick.kaml.github.yaml

import com.amihaiemil.eoyaml.Yaml
import com.amihaiemil.eoyaml.YamlMapping
import com.amihaiemil.eoyaml.YamlMappingBuilder
import com.amihaiemil.eoyaml.YamlNode
import com.amihaiemil.eoyaml.YamlSequenceBuilder
import com.github.seepick.kaml.github.domain.CronTrigger
import com.github.seepick.kaml.github.domain.GenericStep
import com.github.seepick.kaml.github.domain.GithubAction
import com.github.seepick.kaml.github.domain.Job
import com.github.seepick.kaml.github.domain.ManualTrigger
import com.github.seepick.kaml.github.domain.OnPushBranchTrigger
import com.github.seepick.kaml.github.domain.RunStep
import com.github.seepick.kaml.github.domain.Step
import com.github.seepick.kaml.github.domain.Trigger

fun YamlSequenceBuilder.addAllNodes(nodes: List<YamlNode>) = apply {
    nodes.forEach(::add)
}

fun YamlSequenceBuilder.addAllStrings(nodes: List<String>) = apply {
    nodes.forEach(::add)
}

private fun yamlMap() = Yaml.createMutableYamlMappingBuilder()

private fun yamlSeq() = Yaml.createMutableYamlSequenceBuilder()

private fun yamlScal() = Yaml.createYamlScalarBuilder()

private fun yamlScal(value: String) = Yaml.createYamlScalarBuilder().buildPlainScalar(value)

fun GithubAction.toYamlString(): String {
    val root = yamlMap()
    root.add("name", name)
    if (triggers.isNotEmpty()) {
        root.add("on", triggersYaml(triggers))
    }
    if (jobs.isNotEmpty()) {
        root.add("jobs", jobsYaml(jobs))
    }
//    val printer = Yaml.createYamlPrinter(FileWriter("/path/to/map.yml"))
//    printer.print(map)
    return root.build().toString()
        .let(::fixDashPlacement) // GitHubAction, steps mapping-sequence with "-" prefixed
        .also { println(it) }
}

private fun triggersYaml(triggers: List<Trigger>): YamlMapping {
    val rootTriggers = yamlMap()
    triggers.forEach { trigger ->
        when (trigger) {
            ManualTrigger -> {
                rootTriggers.add("workflow_dispatch", yamlScal().buildPlainScalar(""))
            }

            is CronTrigger -> {
                rootTriggers.add("schedule", yamlSeq().add(yamlMap().add("cron", trigger.pattern).build()).build())
            }

            is OnPushBranchTrigger -> {
                rootTriggers.add(
                    "push",
                    yamlMap().add(
                        "branches",
                        yamlSeq().addAllStrings(trigger.branchNames).build(),
                    ).build(),
                )
            }
        }
    }
    return rootTriggers.build()
}

private fun jobsYaml(jobs: List<Job>): YamlMapping {
    val rootJobs = yamlMap()
    jobs.map { job ->
        val jobYaml = yamlMap()
            .add("name", job.name)
            .add("runs-on", job.runsOn.image.coordinates)
        job.environment?.let { env ->
            jobYaml.add("environment", env.yamlValue)
        }
        if (job.permissions.isNotEmpty()) {
            jobYaml.add(
                "permissions",
                job.permissions.fold(yamlMap()) { node, permission ->
                    node.add(permission.type.yamlValue, permission.level.yamlValue)
                }.build(),
            )
        }
        if (job.steps.isNotEmpty()) {
            jobYaml.add(
                "steps",
                yamlSeq().addAllNodes(
                    job.steps.map {
                        stepYaml(it)
                    },
                ).build(),
            )
        }
        rootJobs.add(job.id, jobYaml.build())
    }
    return rootJobs.build()
}

private fun stepYaml(step: Step): YamlMapping {
    val stepYaml = yamlMap().add("name", step.name)
    step.uses?.let { uses ->
        stepYaml.add("uses", uses.coordinates)
    }
    when (step) {
        is GenericStep -> {
            if (step.withParams.isNotEmpty()) {
                stepYaml.add("with", yamlMap().addKeyValues(step.withParams).build())
            }
        }

        is RunStep -> {
            stepYaml.add("run", step.command)
            // TODO support multi-line string properly
//                    .addLine("line1")
//                    .addLine("line2")
//                    .buildLiteralBlockScalar("block comment")
        }
    }
    return stepYaml.build()
}

fun YamlMappingBuilder.addKeyValues(map: Map<String, String>) = apply {
    map.forEach { (key, value) ->
        add(key, value)
    }
}

/** Move a lone "-" line to the same line as the following mapping key.
 *  Example:
 *    "  -\n    name: X"  ->  "  - name: X"
 */
fun fixDashPlacement(yaml: String): String {
    // (?m) = multiline, ^ matches beginning of a line
    // (\\s*) captures indentation, then a '-' alone on its line, then the next line
    // starts with the same indentation plus some more spaces; we replace the matched
    // prefix with "<indent>- " so the following key remains.
    return yaml.replace(Regex("(?m)^(\\s*)-\\s*\\n\\1\\s+"), "$1- ")
}
