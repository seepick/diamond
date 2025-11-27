package nl.uwv.smz.diamond.extern.stub.sftp

import nl.uwv.smz.diamond.extern.api.sftp.SftpClient
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnectConfig
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnector
import java.nio.file.Path
import kotlin.io.path.Path

class SftpClientStub : SftpClient {
    var remoteFiles = setOf(Path("stubPath"))
    var uploadedFiles = mutableListOf<Pair<Path, Path>>()
    var downloadFiles = mutableListOf<Pair<Path, Path>>()
    var disconnectCalled = 0

    override fun listRemoteFiles(remoteDirectoryPath: Path) = remoteFiles

    override fun uploadFile(
        localFilePath: Path,
        remoteFilePath: Path,
    ) {
        uploadedFiles += localFilePath to remoteFilePath
    }

    override fun downloadFile(
        remoteFilePath: Path,
        localFilePath: Path,
    ) {
        downloadFiles += localFilePath to remoteFilePath
    }

    override fun disconnect() {
        disconnectCalled++
    }
}

class SftpConnectorStub : SftpConnector {
    override fun connect(config: SftpConnectConfig) = SftpClientStub()
}
