package nl.uwv.smz.diamond.shared.common.kaml.github.dsl

import nl.uwv.smz.diamond.shared.common.kaml.github.domain.CronTrigger
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.ManualTrigger
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.OnPushBranchTrigger
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.Trigger

class TriggersDsl {
    private val triggers = mutableListOf<Trigger>()

    fun onPushBranches(branch: String, vararg moreBranches: String) {
        triggers += OnPushBranchTrigger(
            buildList {
                add(branch)
                addAll(moreBranches)
            },
        )
    }

    fun cron(pattern: String) {
        triggers += CronTrigger(pattern)
    }

    fun manual() {
        triggers += ManualTrigger
    }

    fun build(): List<Trigger> = triggers
}
