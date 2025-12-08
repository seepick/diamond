package nl.uwv.smz.diamond.shared.common.yaml.github

import nl.uwv.smz.diamond.shared.common.yaml.toYamlString

fun GithubAction.toYamlString() = toYamlTree().toYamlString()

data class GithubAction(
    val name: String,
    // TODO it should not be possible to register the same trigger type twice!
    val triggers: List<Trigger>,
    val jobs: List<Job>
) {
    // easier for testing without ;) maybe configurable { ignore, warn, fail }?
//    init {
//        require(name.isNotEmpty()) { "Name must not be empty!" }
//        require(triggers.isNotEmpty()) { "At least 1 trigger is required!" }
//        require(jobs.isNotEmpty()) { "At least 1 job is required!" }
//    }
}

data class Job(
    val id: String,
    val name: String,
    val runsOn: Runtime,
    val environment: Environment?,
    val permissions: List<Permission>,
    val steps: List<Step>,
)

data class Permission(
    val type: PermissionType,
    val level: PermissionLevel,
)

enum class PermissionType(val yamlValue: String) {
    //  actions
//  checks
    Contents("contents")
//  deployments
//  id
//  issues
//  discussions
//  packages
//  pages
//  pull
//  repository
//  security
//  statuses
}

enum class PermissionLevel(val yamlValue: String) {
    Read("read"),
    Write("write"),
    None("none"),
}

sealed interface Step {
    val name: String
    val uses: Image?
}

data class RunStep(
    override val name: String,
    val command: String,
) : Step {
    override val uses: Image? = null
}

data class GenericStep(
    override val name: String,
    override val uses: Image,
    val withParams: Map<String, String> = emptyMap(),
) : Step

interface Image {
    val group: String?
    val name: String
    val version: String?

    val coordinates get() = (group?.let { "$it/" } ?: "") + name + (version?.let { "@$it" } ?: "")
}

data class GenericImage(
    override val group: String? = null,
    override val name: String,
    override val version: String? = null
) : Image

object Images {
    /** actions/checkout@v4 */
    val checkout = GenericImage(
        group = "actions",
        name = "checkout",
        version = "v4",
    )

    val setupJava = GenericImage(
        group = "actions",
        name = "setup-java",
        version = "v4",
    )

    object Runtime {
        val ubuntuLatest = RuntimeImage(
            GenericImage(
                name = "ubuntu-latest",
            ),
        )
    }
}

data class RuntimeImage(val image: Image) : Image by image

enum class Runtime(val image: RuntimeImage) {
    UbuntuLatest(Images.Runtime.ubuntuLatest);

    companion object {
        val default = UbuntuLatest
    }
}

sealed interface Trigger

data class OnPushBranchTrigger(
    val branchNames: List<String>,
) : Trigger

data class CronTrigger(
    /** E.g.: "0 0 * * *" */
    val pattern: String,
) : Trigger

object ManualTrigger : Trigger
