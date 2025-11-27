package nl.uwv.smz.diamond.extern.impl

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.MountableFile

/** See: https://github.com/testcontainers/testcontainers-java/blob/main/examples/sftp/src/test/java/org/example/SftpContainerTest.java */
// docker run -p 22:22 -d atmoz/sftp foo:pass:::upload
// TODO creates a warning, build for amd64 (not arm64 running here)
// or maybe? https://hub.docker.com/r/instantlinux/proftpd
// or: https://docs.sftpgo.com/2.6/docker/
// or: https://github.com/emberstack/docker-sftp
class SftpContainer : GenericContainer<SftpContainer>("atmoz/sftp:alpine") { // -3.7
    private val port = 22
    private val user = "sftp_user"
    private val password = "sftp_pass"

    init {
        withCopyFileToContainer(
            MountableFile.forClasspathResource("sftpTestcontainersRoot/", 777),
            "/home/$$user/upload/testcontainers",
        )
        withExposedPorts(port)
        // user:pass[:e][:uid[:gid[:dir1[,dir2]...]]] ...,
        withCommand("$user:$password:::upload")

//        setPortBindings(List.of("22:22"))
    }

    val access
        get() =
            SftpAccess(
                host = "localhost",
                port = getMappedPort(port),
                username = user,
                password = password,
            )
}
