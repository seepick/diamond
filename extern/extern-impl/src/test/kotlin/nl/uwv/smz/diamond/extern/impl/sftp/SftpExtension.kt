package nl.uwv.smz.diamond.extern.impl.sftp

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import nl.uwv.smz.diamond.extern.api.sftp.SftpAuthType
import nl.uwv.smz.diamond.extern.api.sftp.SftpClient
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnectConfig
import nl.uwv.smz.diamond.extern.impl.startOrReuseUniqueInstance
import nl.uwv.smz.diamond.extern.impl.toSlf4j
import java.util.UUID

class SftpExtension(val config: SftpContainerConfig) : BeforeTestListener {
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

    fun connectClient() =
        SftpConnectorImpl.connect(
            SftpConnectConfig(
                remoteHost = container.host,
                port = container.getMappedPort(config.port),
                username = config.username,
                auth = SftpAuthType.AuthKey(SftpFiles.privateKey),
                knownHostsFilePath = SftpFiles.knownHosts,
                strictHostChecking = false,
            ),
        )

    fun withClient(code: (SftpClient) -> Unit) {
        val client = connectClient()
        try {
            code(client)
        } finally {
            client.disconnect()
        }
    }
}
