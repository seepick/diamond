package nl.uwv.smz.diamond.extern.api.sftp

import java.io.Closeable
import java.nio.file.Path

interface SftpClient : Closeable {
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
}
