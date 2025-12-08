package nl.uwv.smz.diamond.shared.common.kaml.github.dsl

import nl.uwv.smz.diamond.shared.common.kaml.github.domain.GithubAction
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.Job
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.Trigger

fun githubKaml(code: GithubActionDsl.() -> Unit): GithubAction =
    GithubActionDsl().apply(code).build()

@DslMarker
annotation class GithubDsl

@GithubDsl
class GithubActionDsl {
    var name: String = "Default Action Name"
    private var triggersList = emptyList<Trigger>()
    private var jobsList = emptyList<Job>()

    fun triggers(code: TriggersDsl.() -> Unit) {
        triggersList = TriggersDsl().apply(code).build()
    }

    fun jobs(code: JobsDsl.() -> Unit) {
        jobsList = JobsDsl().apply(code).build()
    }

    internal fun build() = GithubAction(
        name = name,
        triggers = triggersList,
        jobs = jobsList,
    )
}
