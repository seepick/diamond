@Suppress("ConstPropertyName")
object Constants {

    const val assemblyName = "diamond.jar"
    const val kotestTestcontainersTag = "testcontainers" // see: nl.uwv.smz.diamond.shared.test.KoTags

    @Suppress("EnumNaming", "EnumEntryName", "EnumEntryNameCase")
    enum class GradleProperty(val value: String) {
        appVersion("diamond_version"),
        testcontainers("runTestcontainersTests"),
        etests("runEtests"),
    }

    object Fqn {
        const val mainClass = "nl.uwv.smz.diamond.app.DiamondApp"
        const val kotestProjectConfig = "nl.uwv.smz.diamond.shared.test.DiamondKotestProjectConfig"
        const val configDocWriter = "nl.uwv.smz.diamond.app.ConfigDocWriterApp"
    }
}
