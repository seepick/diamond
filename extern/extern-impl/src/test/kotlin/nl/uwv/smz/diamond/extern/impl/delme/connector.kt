package nl.uwv.smz.diamond.extern.impl.delme

import com.jcraft.jsch.JSch

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
    fun connect(
        knownHostsFilePath: String,
        strategy: ConnectionStrategy,
    ): SftpClient

    fun connect(config: SftpConnectConfig): SftpClient
}

class SftpConnectorImpl : SftpConnector {
    override fun connect(
        knownHostsFilePath: String,
        strategy: ConnectionStrategy,
    ): SftpClient =
        when (strategy) {
            is SftpPasswordConnectionStrategyImpl -> SftpSftpClientImpl(strategy.connect(knownHostsFilePath))
            is SftpPrivateKeyConnectionStrategyImpl -> SftpSftpClientImpl(strategy.connect(knownHostsFilePath))
            else -> throw UnsupportedOperationException("Unsupported connection strategy: $strategy")
        }

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
