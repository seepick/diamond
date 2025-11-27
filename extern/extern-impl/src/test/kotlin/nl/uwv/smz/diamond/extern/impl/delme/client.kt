package nl.uwv.smz.diamond.extern.impl.delme

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.Session
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import java.nio.file.Path
import kotlin.io.path.pathString

interface SftpClient {
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

class SftpSftpClientImpl(private val session: Session) : SftpClient {
    private val log = logger {}

    private val channelSftp: ChannelSftp by lazy {
        (session.openChannel("sftp") as ChannelSftp).apply { connect() }
    }

    override fun uploadFile(
        localFilePath: Path,
        remoteFilePath: Path,
    ) {
        log.debug { "uploading file $localFilePath -> $remoteFilePath" }
        channelSftp.put(localFilePath.pathString, remoteFilePath.pathString)
    }

    override fun downloadFile(
        remoteFilePath: Path,
        localFilePath: Path,
    ) {
        log.debug { "downloading file $remoteFilePath -> $localFilePath" }
        channelSftp.get(remoteFilePath.pathString, localFilePath.pathString)
    }

    override fun listRemoteFiles(remoteDirectoryPath: Path): Set<Path> {
        log.debug { "listing files from $remoteDirectoryPath" }
        return channelSftp
            .ls(remoteDirectoryPath.pathString)
            .asSequence()
            .filterIsInstance<ChannelSftp.LsEntry>()
            .map { entry -> remoteDirectoryPath.resolve(entry.filename) }
            .toSet()
            .also { log.debug { "end file list. Found ${it.size} files" } }
    }

    override fun disconnect() =
        try {
            log.debug { "disconnecting" }
            channelSftp.exit()
        } finally {
            session.disconnect()
        }
}
