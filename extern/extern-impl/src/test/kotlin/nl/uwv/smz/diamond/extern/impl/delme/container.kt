package nl.uwv.smz.diamond.extern.impl.delme

import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.builder.Transferable

data class SftpContainerConfig(
    val username: String = "sftpUser",
    val port: Int = 22,
)

class SftpContainer(val config: SftpContainerConfig) : GenericContainer<SftpContainer>("atmoz/sftp") {
    init {
        val sshFiles =
            mapOf(
                "/sftp_ssh/ssh_host_ed25519_key" to "/etc/ssh/ssh_host_ed25519_key",
                "/sftp_ssh/ssh_host_rsa_key" to "/etc/ssh/ssh_host_rsa_key",
                "/sftp_ssh/id_ed25519_client.pub" to "/home/${config.username}/.ssh/keys/id_ed25519_client.pub",
            )

        sshFiles.forEach { (resourcePath, containerPath) ->
            val transferable = Transferable.of(javaClass.getResource(resourcePath).openStream().readAllBytes())
            withCopyToContainer(transferable, containerPath)
        }

        withEnv("SFTP_USERS", "${config.username}::1001::share")
        withCommand("/bin/sh", "-c", "exec /usr/sbin/sshd -D -e")
    }
}
