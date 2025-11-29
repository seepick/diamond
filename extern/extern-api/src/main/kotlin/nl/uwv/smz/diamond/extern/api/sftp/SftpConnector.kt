package nl.uwv.smz.diamond.extern.api.sftp

import com.sksamuel.hoplite.Masked
import nl.uwv.smz.diamond.shared.config.ConfigProperty

interface SftpConnector {
    fun connect(): SftpClient
}

data class SftpConfig(
    @ConfigProperty("Host of the SFTP server.")
    val remoteHost: String,
    @ConfigProperty("Port of the SFTP server.")
    val port: Int = 22,
    @ConfigProperty("Login username.")
    val username: String,
    @ConfigProperty("Whether using password or private key.")
    val authIsPassword: Boolean,
    @ConfigProperty("Either password or private key.")
    val authPasswordOrPrivateKeyPath: Masked,
    @ConfigProperty("Path to SSH known hosts file.")
    val knownHostsFilePath: String,
    @ConfigProperty("Disable security check; do NOT activate in production; pretty please.")
    val strictHostChecking: Boolean = true,
) {
    fun onAuth(isPassword: (String) -> Unit, isPrivateKey: (String) -> Unit) {
        if (authIsPassword) {
            isPassword(authPasswordOrPrivateKeyPath.value)
        } else {
            isPrivateKey(
                authPasswordOrPrivateKeyPath.value
        )
        }
    }
}
