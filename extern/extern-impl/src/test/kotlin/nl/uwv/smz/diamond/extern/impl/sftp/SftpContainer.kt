package nl.uwv.smz.diamond.extern.impl.sftp

import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.builder.Transferable

data class SftpContainerConfig(
    val username: String = "sftpUser",
    val port: Int = 22,
)

// docker run -p 22:22 -d atmoz/sftp foo::::upload
// or maybe? https://hub.docker.com/r/instantlinux/proftpd
// or: https://docs.sftpgo.com/2.6/docker/
// or: https://github.com/emberstack/docker-sftp

class SftpContainer(val config: SftpContainerConfig) : GenericContainer<SftpContainer>("atmoz/sftp") {
    init {
        val sshFiles =
            mapOf(
                SftpFiles.hostEdKey to "/etc/ssh/ssh_host_ed25519_key",
                SftpFiles.hostRsaKey to "/etc/ssh/ssh_host_rsa_key",
                SftpFiles.clientPub to "/home/${config.username}/.ssh/keys/id_ed25519_client.pub",
            )

        sshFiles.forEach { (resourcePath, containerPath) ->
            val transferable = Transferable.of(javaClass.getResource(resourcePath)!!.openStream().readAllBytes())
            withCopyToContainer(transferable, containerPath)
        }
//        withCopyFileToContainer(
//            MountableFile.forClasspathResource("sftpTestcontainersRoot/", 777),
//            "/home/$user/upload/testcontainers",
//        )

        // TODO withEnv("SFTP_USERS", "$user:$password:1001::share") ???
        // user:pass[:e][:uid[:gid[:dir1[,dir2]...]]] ...,
        withEnv("SFTP_USERS", "${config.username}::1001::share")
        withCommand("/bin/sh", "-c", "exec /usr/sbin/sshd -D -e")
        // withExposedPorts(port) no => see: startOrReuseUniqueInstance
        // setPortBindings(List.of("22:22"))
    }
}
