package com.github.seepick.kaml.github.dsl

import com.github.seepick.kaml.github.domain.CronTrigger
import com.github.seepick.kaml.github.domain.ManualTrigger
import com.github.seepick.kaml.github.domain.OnPushBranchTrigger
import com.github.seepick.kaml.github.domain.Trigger

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
