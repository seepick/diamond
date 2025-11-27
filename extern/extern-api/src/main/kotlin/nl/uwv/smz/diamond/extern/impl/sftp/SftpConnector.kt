package nl.uwv.smz.diamond.extern.impl.sftp

interface SftpConnector {
    fun connect(config: SftpConnectConfig): SftpClient
}

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
