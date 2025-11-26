package nl.uwv.smz.diamond.app

import nl.uwv.smz.diamond.shared.common.Constants
import nl.uwv.smz.diamond.shared.logging.LogLevel
import nl.uwv.smz.diamond.shared.logging.reconfigureLogback

object LocalDiamondApp {

    private val localConfig = Config(
        database = DatabaseMetaConfig(
            mode = PersistenceMode.StubMode,
            // TODO maybe per user?! so can switch between fast stub, regular H2, or heavy oracle?!
//            DatabaseConfig(
//                url = "jdbc:h2:mem:localDb;DB_CLOSE_DELAY=-1",
//                username = "",
//                password = Masked("")
//            )
        ),
        ktor = KtorConfig(port = 8000)
    )

    @JvmStatic
    fun main(args: Array<String>) {
        DiamondApp.startApp(
            defaultLog = ::reconfigureLog,
            defaultConfig = { localConfig }
        )

    }

    private fun reconfigureLog() {
        reconfigureLogback {
            rootLevel = LogLevel.Warn
            addConsoleAppender {
                pattern = "%d{HH:mm:ss.SSS} [%-5level] %logger{42} - %msg%n"
            }
            packageLevel(LogLevel.Trace, Constants.ROOT_PACKAGE_NAME)
        }
    }
}
