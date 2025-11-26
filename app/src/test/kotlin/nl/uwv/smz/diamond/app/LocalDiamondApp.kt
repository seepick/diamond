package nl.uwv.smz.diamond.app

import com.sksamuel.hoplite.Secret
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig
import nl.uwv.smz.diamond.shared.common.Constants
import nl.uwv.smz.diamond.shared.logging.LogLevel
import nl.uwv.smz.diamond.shared.logging.reconfigureLogback

object LocalDiamondApp {

    private val dbStubConfig = DatabaseConfig(
        stubEnabled = true,
        url = "",
        username = "",
        password = Secret(""),
    )
//    private val dbH2Config = DatabaseConfig(
//        stubEnabled = false, url = "jdbc:h2:mem:localDb;DB_CLOSE_DELAY=-1", username = "", password = Secret("")
//    )
//    private val dbOracleDockerConfig = DatabaseConfig(
//        stubEnabled = false, url = "jdbc:h2:mem:localDb;DB_CLOSE_DELAY=-1", username = "", password = Secret("")
//    )

    // TODO maybe per user?! so can switch between fast stub, regular H2, or heavy oracle?!
    private val localConfig = Config(
        database = dbStubConfig, server = ServerConfig(port = 8000)
    )

    @JvmStatic
    fun main(args: Array<String>) {
        DiamondApp.startApp(
            defaultLog = ::reconfigureLog,
            defaultConfig = { localConfig },
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
