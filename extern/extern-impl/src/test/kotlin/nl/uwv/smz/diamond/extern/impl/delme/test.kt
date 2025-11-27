package nl.uwv.smz.diamond.extern.impl.delme

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import java.nio.file.Path
import java.util.UUID
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile

private val logger: KLogger = KotlinLogging.logger {}

class SftpFileConnectorServiceImplTest() : StringSpec() {
    private lateinit var sftpContainer: SftpContainer
    private lateinit var knownHostsFilePathString: String
    private lateinit var connectionStrategy: ConnectionStrategy
    private val instanceUUID = UUID.randomUUID().toString()

    override suspend fun beforeTest(testCase: TestCase) {
        sftpContainer =
            SftpContainer().apply {
                startOrReuseUniqueInstance(
                    instanceUUID,
                    exposedPorts = intArrayOf(SftpContainer.PORT),
                    logger = logger.toSlf4j(),
                )
            }
        knownHostsFilePathString = javaClass.getResource("/sftp_ssh/known_hosts").file
        connectionStrategy =
            SftpPrivateKeyConnectionStrategyImpl(
                username = SftpContainer.SFTP_USERNAME_TEST,
                privateKey = javaClass.getResource("/sftp_ssh/id_ed25519_client").file,
                remoteHost = sftpContainer.host,
                port = sftpContainer.getMappedPort(SftpContainer.PORT),
                strictHostChecking = true,
            )
    }

    init {
        "Upload and download should work without exception with private key" {
            val remoteFilePath = Path("/share/${UUID.randomUUID()}.tmp")
            val downloadedFilePath: Path = createOutputTempDirectory().resolve(remoteFilePath.fileName)
            val connection: Connection =
                SftpFileConnectorServiceImpl(knownHostsFilePathString).connect(connectionStrategy)

            connection.uploadFile(
                localFilePath = createInputTempFile(),
                remoteFilePath = remoteFilePath,
            )

            connection
                .listRemoteFiles(remoteFilePath.parent)
                .contains(remoteFilePath) shouldBe true

            connection.downloadFile(
                remoteFilePath = remoteFilePath,
                localFilePath = downloadedFilePath,
            )
            downloadedFilePath.exists() shouldBe true
            downloadedFilePath.isRegularFile() shouldBe true

            connection.disconnect()
        }
    }
}
