package nl.uwv.smz.diamond.extern.impl.delme

import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.builder.Transferable

class SftpContainer() : GenericContainer<SftpContainer>(IMAGE) {
    companion object {
        const val IMAGE: String = "atmoz/sftp"
        const val PORT: Int = 22
        const val SFTP_USERNAME_TEST = "foo"
    }

    init {
        val sshFiles =
            mapOf(
                "/sftp_ssh/ssh_host_ed25519_key" to "/etc/ssh/ssh_host_ed25519_key",
                "/sftp_ssh/ssh_host_rsa_key" to "/etc/ssh/ssh_host_rsa_key",
                "/sftp_ssh/id_ed25519_client.pub" to "/home/$SFTP_USERNAME_TEST/.ssh/keys/id_ed25519_client.pub",
            )

        sshFiles.forEach { (resourcePath, containerPath) ->
            val transferable = Transferable.of(javaClass.getResource(resourcePath).openStream().readAllBytes())
            withCopyToContainer(transferable, containerPath)
        }

        withEnv("SFTP_USERS", "$SFTP_USERNAME_TEST::1001::share")
        withCommand("/bin/sh", "-c", "exec /usr/sbin/sshd -D -e")
    }
}
