package nl.uwv.smz.diamond.extern.impl

import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.builder.Transferable

// docker run -p 22:22 -d atmoz/sftp foo:pass:::upload
// TODO creates a warning, build for amd64 (not arm64 running here)
// or maybe? https://hub.docker.com/r/instantlinux/proftpd
// or: https://docs.sftpgo.com/2.6/docker/
// or: https://github.com/emberstack/docker-sftp

/** See: https://github.com/testcontainers/testcontainers-java/blob/main/examples/sftp/src/test/java/org/example/SftpContainerTest.java */
class SftpContainer : GenericContainer<SftpContainer>("atmoz/sftp") { // :alpine-3.7
    private val port = 22
    private val user = "foo"
    private val password = ""

    init {
        val sshFiles =
            mapOf(
                "/sftp_ssh/ssh_host_ed25519_key" to "/etc/ssh/ssh_host_ed25519_key",
                "/sftp_ssh/ssh_host_rsa_key" to "/etc/ssh/ssh_host_rsa_key",
                "/sftp_ssh/id_ed25519_client.pub" to "/home/$user/.ssh/keys/id_ed25519_client.pub",
            )

        sshFiles.forEach { (resourcePath, containerPath) ->
            withCopyToContainer(
                Transferable.of(javaClass.getResource(resourcePath).openStream().readAllBytes()), // contentAsByteArray
                containerPath,
            )
        }
        withExposedPorts(port)
        withEnv("SFTP_USERS", "$user:$password:1001::share")
        withCommand("/bin/sh", "-c", "exec /usr/sbin/sshd -D -e")
//        withCommand("$user:$password:1001::upload")

//        withCopyFileToContainer(
//            MountableFile.forClasspathResource("sftpTestcontainersRoot/", 777),
//            "/home/$user/upload/testcontainers",
//        )
//        // user:pass[:e][:uid[:gid[:dir1[,dir2]...]]] ...,

//        setPortBindings(List.of("22:22"))
    }

    /*

    fun GenericContainer<*>.startOrReuseUniqueInstance(
        instanceUUID: String,
        tmpFolderName: String = instanceUUID,
        env: Map<String, String> = emptyMap(),
        vararg exposedPorts: Int,
        logger: Logger? = null
    ): GenericContainer<*> {
        withTmpFs(makeTmpFs(tmpFolderName))
        env.forEach { addEnv(it.key, it.value) }
        if (exposedPorts.isNotEmpty()) withExposedPorts(*exposedPorts.toTypedArray())
        withImagePullPolicy(PullPolicy.ageBased(Duration.ofDays(30)))
        withStartupAttempts(1)
        withReuse(true)
        withLabel(getReuseLabel(), instanceUUID)
        start()
        logger?.let {
            followOutput(Slf4jLogConsumer(it).withSeparateOutputStreams())
        }
        return this
    }
     */
    val access
        get() =
            SftpAccess(
                host = "localhost",
                port = getMappedPort(port),
                username = user,
                password = password,
            )
}
