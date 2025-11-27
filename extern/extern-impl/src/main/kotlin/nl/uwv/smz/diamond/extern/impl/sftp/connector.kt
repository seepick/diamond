package nl.uwv.smz.diamond.extern.impl.sftp

import com.jcraft.jsch.JSch

// inspired by https://github.com/alkaphreak/marstech-sftp/blob/main/src/main/kotlin/fr/marstech/mtsftp/service/SftpFileConnectorServiceImpl.kt

data class SftpConnectConfig(
    val remoteHost: String,
    val port: Int?,
    val username: String,
    val auth: SftpAuthType,
    val knownHostsFilePath: String,
    val strictHostChecking: Boolean = true,
)

sealed interface SftpAuthType {
    data class AuthPassword(val password: String) : SftpAuthType

    data class AuthKey(val privateKeyPath: String) : SftpAuthType
}

interface SftpConnector {
    fun connect(config: SftpConnectConfig): SftpClient
}

object SftpConnectorImpl : SftpConnector {
    override fun connect(config: SftpConnectConfig): SftpClient {
        val jsch = JSch()
        jsch.setKnownHosts(config.knownHostsFilePath)
        val session =
            config.port?.let { port ->
                jsch.getSession(config.username, config.remoteHost, port)
            } ?: jsch.getSession(config.username, config.remoteHost)
        when (config.auth) {
            is SftpAuthType.AuthKey -> jsch.addIdentity(config.auth.privateKeyPath)
            is SftpAuthType.AuthPassword -> session.setPassword(config.auth.password)
        }
        session.setConfig("StrictHostKeyChecking", if (config.strictHostChecking) "yes" else "no")
        session.setConfig("HashKnownHosts", "yes")
        session.connect()
        return SftpSftpClientImpl(session)
    }
}
