package nl.uwv.smz.diamond.extern.impl.sftp

import com.jcraft.jsch.JSch
import nl.uwv.smz.diamond.extern.api.sftp.SftpAuthType
import nl.uwv.smz.diamond.extern.api.sftp.SftpClient
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnectConfig
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnector

object SftpConnectorImpl : SftpConnector {
    private const val CONNECTION_TIMEOUT = 10_000

    override fun connect(config: SftpConnectConfig): SftpClient {
        val jsch = JSch()
        jsch.setKnownHosts(config.knownHostsFilePath)
        val session = jsch.connectSession(config)
        when (val auth = config.auth) {
            is SftpAuthType.AuthKey -> jsch.addIdentity(auth.privateKeyPath)
            is SftpAuthType.AuthPassword -> session.setPassword(auth.password)
        }
        session.setConfig("StrictHostKeyChecking", if (config.strictHostChecking) "yes" else "no")
        session.setConfig("HashKnownHosts", "yes")
        session.connect(CONNECTION_TIMEOUT)
        return SftpSftpClientImpl(session)
    }

    private fun JSch.connectSession(config: SftpConnectConfig) =
        config.port?.let { port ->
            getSession(config.username, config.remoteHost, port)
        } ?: getSession(config.username, config.remoteHost)
}
