@Suppress("EnumNaming", "EnumEntryName", "EnumEntryNameCase")
enum class GradleProperty(val value: String) {
    // injected variables
    appVersion("diamond_version"),
    branchName("diamond_branch"),

    // profiles
    testcontainers("runTestcontainersTests"),
    etests("runEtests"),

    // build flags
    isCi("isCi"),
    enableOwasp("enableOwasp");

    fun isSet(): Boolean = System.getProperty(value) != null

    fun get(): String? = System.getProperty(value)
    // NO! don't use -P properties as it requires a Project to be available; complicates build
    // fun Project.hasGradleProperty(property: Constants.GradleProperty): Boolean =
    // providers.gradleProperty(property.value).isPresent
}
