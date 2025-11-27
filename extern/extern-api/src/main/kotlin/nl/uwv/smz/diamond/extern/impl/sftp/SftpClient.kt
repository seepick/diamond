package nl.uwv.smz.diamond.extern.impl.sftp

import java.nio.file.Path

interface SftpClient {
    // TODO use arrow-either instead (need to translate exceptions)
    fun listRemoteFiles(remoteDirectoryPath: Path): Set<Path>

    fun uploadFile(
        localFilePath: Path,
        remoteFilePath: Path,
    )

    fun downloadFile(
        remoteFilePath: Path,
        localFilePath: Path,
    )

    fun disconnect()
}
