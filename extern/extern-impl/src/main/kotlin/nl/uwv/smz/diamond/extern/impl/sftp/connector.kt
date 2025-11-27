package nl.uwv.smz.diamond.extern.impl.sftp

import com.jcraft.jsch.JSch

// inspired by https://github.com/alkaphreak/marstech-sftp/blob/main/src/main/kotlin/fr/marstech/mtsftp/service/SftpFileConnectorServiceImpl.kt
// also see: https://medium.com/whozapp/sftp-test-implem-of-jsch-with-kotlin-testcontainers-and-spring-boot-native-537f624da895

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
