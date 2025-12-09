@Suppress("ConstPropertyName")
object Constants {

    const val assemblyName = "diamond.jar"
    const val kotestTestcontainersTag = "testcontainers" // see: nl.uwv.smz.diamond.shared.test.KoTags

    object Fqn {
        const val mainClass = "nl.uwv.smz.diamond.app.DiamondApp"
        const val localMainClass = "nl.uwv.smz.diamond.app.LocalDiamondApp"
        const val kotestProjectConfig = "nl.uwv.smz.diamond.shared.test.DiamondKotestProjectConfig"
        const val configDocWriter = "nl.uwv.smz.diamond.app.ConfigDocWriterApp"
        const val kamlGenerator = "nl.uwv.smz.diamond.root.GenerateKamlApp"
    }
}
