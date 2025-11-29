package nl.uwv.smz.diamond.extern.impl.sftp

import com.sksamuel.hoplite.Secret
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import io.kotest.engine.test.TestResult
import nl.uwv.smz.diamond.extern.api.sftp.SftpClient
import nl.uwv.smz.diamond.extern.api.sftp.SftpConfig
import nl.uwv.smz.diamond.extern.impl.startOrReuseUniqueInstance
import nl.uwv.smz.diamond.extern.impl.toSlf4j
import java.util.UUID

class SftpExtension(val config: SftpContainerConfig) : BeforeTestListener, AfterTestListener {
    private val log = logger {}
    private lateinit var container: SftpContainer

    override suspend fun beforeTest(testCase: TestCase) {
        container =
            SftpContainer(config).apply {
                startOrReuseUniqueInstance(
                    tmpFolderName = UUID.randomUUID().toString(),
                    exposedPorts = intArrayOf(config.port),
                    logger = log.toSlf4j(),
                )
            }
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        container.close()
    }

    private fun connectClient() =
        SftpConnectorImpl(
            SftpConfig(
                remoteHost = container.host,
                port = container.getMappedPort(config.port),
                username = config.username,
                authIsPassword = false,
                authPasswordOrPrivateKeyPath = Secret(SftpFiles.clientPrivKey),
                knownHostsFilePath = SftpFiles.knownHosts,
                strictHostChecking = false,
            ),
        ).connect()

    fun withClient(code: (SftpClient) -> Unit) {
        connectClient().use { client ->
            code(client)
        }
    }
}
