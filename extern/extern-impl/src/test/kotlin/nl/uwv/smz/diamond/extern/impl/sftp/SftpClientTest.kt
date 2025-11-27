package nl.uwv.smz.diamond.extern.impl.sftp

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.util.UUID
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile

class SftpClientTest : StringSpec({
    val sftp = SftpExtension(SftpContainerConfig())
    extension(sftp)

    "Upload and download should work with private key" {
        sftp.withClient { client ->
            val remoteFilePath = Path("/share/${UUID.randomUUID()}.tmp")
            client.uploadFile(
                localFilePath = Files.createTempFile("file-in", ".tmp"),
                remoteFilePath = remoteFilePath,
            )

            client
                .listRemoteFiles(remoteFilePath.parent)
                .contains(remoteFilePath) shouldBe true

            val downloadedFilePath = Files.createTempDirectory("files-out").resolve(remoteFilePath.fileName)
            client.downloadFile(
                remoteFilePath = remoteFilePath,
                localFilePath = downloadedFilePath,
            )
            downloadedFilePath.exists() shouldBe true
            downloadedFilePath.isRegularFile() shouldBe true
        }
    }
})
