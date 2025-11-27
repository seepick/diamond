package nl.uwv.smz.diamond.extern.impl.sftp

import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.builder.Transferable

data class SftpContainerConfig(
    val username: String = "sftpUser",
    val port: Int = 22,
)

// docker run -p 22:22 -d atmoz/sftp foo:pass:::upload
// or maybe? https://hub.docker.com/r/instantlinux/proftpd
// or: https://docs.sftpgo.com/2.6/docker/
// or: https://github.com/emberstack/docker-sftp

class SftpContainer(val config: SftpContainerConfig) : GenericContainer<SftpContainer>("atmoz/sftp") {
    init {
        val sshFiles =
            mapOf(
                "/sftp_ssh/ssh_host_ed25519_key" to "/etc/ssh/ssh_host_ed25519_key",
                "/sftp_ssh/ssh_host_rsa_key" to "/etc/ssh/ssh_host_rsa_key",
                "/sftp_ssh/id_ed25519_client.pub" to "/home/${config.username}/.ssh/keys/id_ed25519_client.pub",
            )

        sshFiles.forEach { (resourcePath, containerPath) ->
            val transferable = Transferable.of(javaClass.getResource(resourcePath)!!.openStream().readAllBytes())
            withCopyToContainer(transferable, containerPath)
        }

        // TODO withEnv("SFTP_USERS", "$user:$password:1001::share") ???
        withEnv("SFTP_USERS", "${config.username}::1001::share")
        withCommand("/bin/sh", "-c", "exec /usr/sbin/sshd -D -e")

//        withExposedPorts(port) no => see:  startOrReuseUniqueInstance

//        withCopyFileToContainer(
//            MountableFile.forClasspathResource("sftpTestcontainersRoot/", 777),
//            "/home/$user/upload/testcontainers",
//        )
//        // user:pass[:e][:uid[:gid[:dir1[,dir2]...]]] ...,

//        setPortBindings(List.of("22:22"))
    }
}
