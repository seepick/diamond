package nl.uwv.smz.diamond.extern.impl.sftp

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
    companion object {
        private const val SESSION_TIMEOUT = 5_000
    }

    private val log = logger {}

    private val channel: ChannelSftp by lazy {
        (session.openChannel("sftp") as ChannelSftp).apply { connect(SESSION_TIMEOUT) }
    }

    // TODO needs to translate exceptions to Either.left
    override fun uploadFile(
        localFilePath: Path,
        remoteFilePath: Path,
    ) {
        log.debug { "uploading file $localFilePath -> $remoteFilePath" }
        channel.put(localFilePath.pathString, remoteFilePath.pathString)
    }

    override fun downloadFile(
        remoteFilePath: Path,
        localFilePath: Path,
    ) {
        log.debug { "downloading file $remoteFilePath -> $localFilePath" }
        channel.get(remoteFilePath.pathString, localFilePath.pathString)
    }

    override fun listRemoteFiles(remoteDirectoryPath: Path): Set<Path> {
        log.debug { "listing files from $remoteDirectoryPath" }
        return channel
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
            channel.exit()
        } finally {
            session.disconnect()
        }
}
