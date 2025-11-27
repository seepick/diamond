package nl.uwv.smz.diamond.extern.impl.delme

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import io.github.oshai.kotlinlogging.KotlinLogging
import java.nio.file.Path
import kotlin.io.path.pathString

private val logger = KotlinLogging.logger {}

interface Connection {
    fun disconnect()

    fun downloadFile(
        remoteFilePath: Path,
        localFilePath: Path,
    )

    fun listRemoteFiles(remoteDirectoryPath: Path): Set<Path>

    fun uploadFile(
        localFilePath: Path,
        remoteFilePath: Path,
    )
}

interface ConnectionStrategy {
    fun build(knownHostsFilePath: String): JSch = JSch().also { it.setKnownHosts(knownHostsFilePath) }
}

interface FileConnectorService {
    fun connect(strategy: ConnectionStrategy): Connection
}

class SftpFileConnectorServiceImpl(
    var knownHostsFilePath: String,
) : FileConnectorService {
    override fun connect(strategy: ConnectionStrategy): Connection =
        when (strategy) {
            is SftpPasswordConnectionStrategyImpl -> SftpConnectionImpl(strategy.connect(knownHostsFilePath))
            is SftpPrivateKeyConnectionStrategyImpl -> SftpConnectionImpl(strategy.connect(knownHostsFilePath))
            else -> throw UnsupportedOperationException("Unsupported connection strategy: $strategy")
        }
}

class SftpConnectionImpl(private val session: Session) : Connection {
    private val channelSftp: ChannelSftp by lazy {
        (session.openChannel("sftp") as ChannelSftp).apply { connect() }
    }

    override fun uploadFile(
        localFilePath: Path,
        remoteFilePath: Path,
    ) {
        logger.debug { "upload file from $localFilePath to $remoteFilePath" }
        channelSftp.put(localFilePath.pathString, remoteFilePath.pathString)
        logger.debug { "file uploaded" }
    }

    override fun downloadFile(
        remoteFilePath: Path,
        localFilePath: Path,
    ) {
        logger.debug { "download file from $remoteFilePath to $localFilePath" }
        channelSftp.get(remoteFilePath.pathString, localFilePath.pathString)
        logger.debug { "file downloaded" }
    }

    override fun listRemoteFiles(remoteDirectoryPath: Path): Set<Path> {
        logger.debug { "listing files from $remoteDirectoryPath" }
        return channelSftp
            .ls(remoteDirectoryPath.pathString)
            .asSequence()
            .filterIsInstance<ChannelSftp.LsEntry>()
            .map { entry -> remoteDirectoryPath.resolve(entry.filename) }
            .toSet()
            .also { logger.debug { "end file list. Found ${it.size} files" } }
    }

    override fun disconnect() =
        try {
            channelSftp.exit()
        } finally {
            session.disconnect()
        }
}

class SftpPasswordConnectionStrategyImpl(
    private val username: String,
    private val password: String,
    private val remoteHost: String,
    private val port: Int?,
    private val strictHostChecking: Boolean = true,
) : ConnectionStrategy {
    fun connect(knownHostsFilePath: String): Session =
        build(knownHostsFilePath)
            .run {
                logger.debug { "connectWithPassword on host $remoteHost:$port" }
                port?.let { getSession(username, remoteHost, it) } ?: getSession(username, remoteHost)
            }.also {
                it.setPassword(password)
                it.setConfig("StrictHostKeyChecking", if (strictHostChecking) "yes" else "no")
                it.setConfig("HashKnownHosts", "yes")
                it.connect()
                logger.debug { "session opened on remote host" }
            }
}

class SftpPrivateKeyConnectionStrategyImpl(
    private val username: String,
    private val privateKey: String,
    private val remoteHost: String,
    private val port: Int?,
    private val strictHostChecking: Boolean = true,
) : ConnectionStrategy {
    fun connect(knownHostsFilePath: String): Session =
        build(knownHostsFilePath)
            .apply { addIdentity(privateKey) }
            .run {
                logger
                    .debug { "connectWithPrivateKey on host $remoteHost:$port" }
                port?.let { getSession(username, remoteHost, it) } ?: getSession(username, remoteHost)
            }.also {
                it.setConfig("StrictHostKeyChecking", if (strictHostChecking) "yes" else "no")
                it.setConfig("HashKnownHosts", "yes")
                it.connect()
                logger
                    .debug { "session opened on remote host" }
            }
}
