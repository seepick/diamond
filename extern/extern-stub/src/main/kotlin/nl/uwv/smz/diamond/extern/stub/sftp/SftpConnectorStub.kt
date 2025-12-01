package nl.uwv.smz.diamond.extern.stub.sftp

import nl.uwv.smz.diamond.extern.api.sftp.SftpClient
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnector
import nl.uwv.smz.diamond.shared.common.HealthState
import nl.uwv.smz.diamond.shared.common.ServiceHealthInfo
import java.nio.file.Path

class SftpClientStub : SftpClient {

    var remoteFiles = emptySet<Path>()
    var uploadedFiles = mutableListOf<Pair<Path, Path>>()
    var downloadFiles = mutableListOf<Pair<Path, Path>>()
    var closeCalled = 0

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

    override fun close() {
        closeCalled++
    }
}

class SftpConnectorStub : SftpConnector {
    override fun connect() = SftpClientStub()

    override fun healthInfo() = ServiceHealthInfo(
        serviceName = "SFTP Stub",
        pingTimeInMs = 0,
        state = HealthState.Healthy,
    )
}
