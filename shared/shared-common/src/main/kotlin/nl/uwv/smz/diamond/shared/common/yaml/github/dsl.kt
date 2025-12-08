package nl.uwv.smz.diamond.shared.common.yaml.github

fun githubYaml(dsl: GithubActionDsl.() -> Unit): GithubAction {
    val impl = GithubActionDsl()
    impl.dsl()
    return impl.build()
}

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
        jobsList = JobsDsl().apply(code).jobs
    }

    internal fun build() = GithubAction(
        name = name,
        triggers = triggersList,
        jobs = jobsList,
    )
}

@GithubDsl
class JobsDsl {
    val jobs = mutableListOf<Job>()

    fun job(code: JobDsl.() -> Unit) {
        jobs += JobDsl().apply(code).build()
        // FIXME build and return
    }
}

interface DslBuilder<T> {
    fun build(): T
}

fun <T, Builder : DslBuilder<T>> Builder.applyAndBuild(code: Builder.() -> Unit) = apply(code).build()

// should be declared outside (custom) by the project
enum class DemoEnvironmentNamespace(override val yamlValue: String) : Environment {
    Production("prod"),
    Acceptance("acc"),
    Test("test"),
    Development("dev"),
}

interface Environment {
    val yamlValue: String
}

@GithubDsl
class JobDsl {
    var id: String = "defaultJobId"
    var name: String = "Default Job Name"
    var runsOn: Runtime = Runtime.default
    var permissions = emptyList<Permission>()
    var environment: Environment? = null
    private var steps = listOf<Step>()

    fun steps(code: StepsDsl.() -> Unit) {
        steps = StepsDsl().apply(code).steps
    }

    fun permissions(code: PermissionsDsl.() -> Unit) {
        permissions = PermissionsDsl().apply(code).build()
    }

    fun build() = Job(
        id = id,
        name = name,
        environment = environment,
        permissions = permissions,
        runsOn = runsOn,
        steps = steps,
    )
}

@GithubDsl
class PermissionsDsl : DslBuilder<List<Permission>> {
    var contents: PermissionLevel? = null
    // add more

    override fun build() = mapOf(
        PermissionType.Contents to contents,
    ).mapNotNull { it.value?.let { value -> Permission(it.key, value) } }
}

@GithubDsl
class StepsDsl : DslBuilder<List<Step>> {
    // if own module, then these internal are not seen (no need for interface abstraction)
    internal val steps = mutableListOf<Step>()

    fun checkout(code: CheckoutDsl.() -> Unit) {
        steps += CheckoutDsl().apply(code).build()
    }

    fun setupJava(code: SetupJavaDsl.() -> Unit) {
        steps += SetupJavaDsl().apply(code).build()
    }

    fun runCommand(code: RunCommandDsl.() -> Unit) {
        steps += RunCommandDsl().apply(code).build()
    }

    override fun build() = steps
}

@GithubDsl
class CheckoutDsl {
    var name: String = "Checkout Code"

    fun build() = GenericStep(
        name = name,
        uses = Images.checkout,
    )
}

@GithubDsl
class SetupJavaDsl {
    var name: String = "Setup JDK"
    var distribution: String = "temurin"
    var javaVersion: JavaVersion = JavaVersion.v17

    fun build() = GenericStep(
        name = name,
        uses = Images.setupJava,
        withParams = mapOf(
            "distribution" to distribution,
            "java-version" to javaVersion.asString,
        ),
    )
}

@GithubDsl
class RunCommandDsl {
    var name: String = "Run Command"
    var command: String = "echo \"No actual run command defined!\";"

    fun build() = RunStep(
        name = name,
        command = command,
    )
}

@ConsistentCopyVisibility
data class JavaVersion private constructor(val asString: String) {
    companion object {
        val v17 = JavaVersion("17")

        fun parse(string: String): JavaVersion {
            // TODO run some checks...
            return JavaVersion(string)
        }
    }
}
