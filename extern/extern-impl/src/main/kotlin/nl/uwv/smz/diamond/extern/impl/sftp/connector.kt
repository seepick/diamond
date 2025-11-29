package nl.uwv.smz.diamond.extern.impl.sftp

import com.jcraft.jsch.JSch
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.extern.api.sftp.SftpClient
import nl.uwv.smz.diamond.extern.api.sftp.SftpConfig
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnector

class SftpConnectorImpl(private val config: SftpConfig) : SftpConnector {

    companion object {
        private const val CONNECTION_TIMEOUT = 10_000
    }

    private val log = logger {}

    override fun connect(): SftpClient {
        val jsch = JSch()
        jsch.setKnownHosts(config.knownHostsFilePath)
        val session = jsch.getSession(config.username, config.remoteHost, config.port)
        config.onAuth(
            isPassword = { session.setPassword(it) },
            isPrivateKey = { jsch.addIdentity(it) },
        )
        session.setConfig("StrictHostKeyChecking", if (config.strictHostChecking) "yes" else "no")
        session.setConfig("HashKnownHosts", "yes")
        log.info { "Connecting to ${config.username}@${config.remoteHost}:${config.port}" }
        session.connect(CONNECTION_TIMEOUT)
        return SftpSftpClientImpl(session)
    }
}
