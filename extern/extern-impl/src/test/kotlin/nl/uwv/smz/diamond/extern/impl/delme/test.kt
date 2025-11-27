package nl.uwv.smz.diamond.extern.impl.delme

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.util.UUID
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile

class SftpListener(val config: SftpContainerConfig) : BeforeTestListener {
    private val log: KLogger = logger {}
    private val instanceUUID = UUID.randomUUID().toString()
    lateinit var sftpContainer: SftpContainer
    lateinit var knownHostsFilePathString: String
//    lateinit var connectionStrategy: ConnectionStrategy

    override suspend fun beforeTest(testCase: TestCase) {
        sftpContainer =
            SftpContainer(config).apply {
                startOrReuseUniqueInstance(
                    instanceUUID,
                    exposedPorts = intArrayOf(config.port),
                    logger = log.toSlf4j(),
                )
            }
        knownHostsFilePathString = javaClass.getResource("/sftp_ssh/known_hosts").file
//        connectionStrategy =
//            SftpPrivateKeyConnectionStrategyImpl(
//                username = config.username,
//                privateKey = javaClass.getResource("/sftp_ssh/id_ed25519_client").file,
//                remoteHost = sftpContainer.host,
//                port = sftpContainer.getMappedPort(config.port),
//                strictHostChecking = true,
//            )
    }
}

class SftpFileConnectorServiceImplTest : StringSpec({
    val config = SftpContainerConfig()
    val sftpListener = SftpListener(config)
    extension(sftpListener)

    "Upload and download should work without exception with private key" {
        val remoteFilePath = Path("/share/${UUID.randomUUID()}.tmp")
        val downloadedFilePath = Files.createTempDirectory("files-out").resolve(remoteFilePath.fileName)
        val sftpClient: SftpClient =
            SftpConnectorImpl().connect(
                SftpConnectConfig(
                    remoteHost = sftpListener.sftpContainer.host,
                    port = sftpListener.sftpContainer.getMappedPort(config.port),
                    username = config.username,
                    auth = SftpAuthType.AuthKey(javaClass.getResource("/sftp_ssh/id_ed25519_client").file),
                    knownHostsFilePath = sftpListener.knownHostsFilePathString,
                    strictHostChecking = false,
                ),
            )
//            SftpConnectorImpl().connect(sftpListener.knownHostsFilePathString, sftpListener.connectionStrategy)

        sftpClient.uploadFile(
            localFilePath = Files.createTempFile("file-in", ".tmp"),
            remoteFilePath = remoteFilePath,
        )

        sftpClient
            .listRemoteFiles(remoteFilePath.parent)
            .contains(remoteFilePath) shouldBe true

        sftpClient.downloadFile(
            remoteFilePath = remoteFilePath,
            localFilePath = downloadedFilePath,
        )
        downloadedFilePath.exists() shouldBe true
        downloadedFilePath.isRegularFile() shouldBe true

        sftpClient.disconnect()
    }
})
