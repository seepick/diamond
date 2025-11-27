package nl.uwv.smz.diamond.extern.impl.sftp

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import com.jcraft.jsch.UserInfo
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.domainFailure.Failure
import java.io.File

data class SftpAccess(
    val host: String,
    val port: Int = 22,
    val username: String,
    val password: String,
)


class SftpImpl {
    private val log = logger {}

    companion object {
        private const val CONNECTION_TIMEOUT = 10_000
        private const val SESSION_TIMEOUT = 5_000
        private const val SFTP_CHANNEL = "sftp"

//    private val channelSftp: ChannelSftp by lazy {
//        (session.openChannel("sftp") as ChannelSftp).apply { connect() }
//    }

//    override fun disconnect() = try {
//        channelSftp.exit()
//    } finally {
//        session.disconnect()
//    }

        // VisibleForTesting (reuse/assert)
        @Suppress("TooGenericExceptionCaught")
        internal fun withChannel(
            access: SftpAccess,
            code: (ChannelSftp) -> Unit,
        ): Either<Failure, Unit> =
            either {
                println("access: $access")
                var session: Session? = null
                try {
                    val jsch = JSch()
                    // jsch.setKnownHosts("/home/xxx/.ssh/known_hosts")
                    jsch.addIdentity(javaClass.getResource("/sftp_ssh/id_ed25519_client").file)
                    val knownHostsFilePathString =
                        javaClass.getResource("/sftp_ssh/known_hosts").file // .toPath().toString()
                    println("knownHostsFilePathString=[$knownHostsFilePathString]")
                    jsch.setKnownHosts(knownHostsFilePathString)

                    session = jsch.getSession(access.username, access.host, access.port)
                    /*
                    connectionStrategy = SftpPrivateKeyConnectionStrategyImpl(
            username = SFTP_USERNAME_TEST,
            privateKey = resourceLoader.getResource("/sftp_ssh/id_ed25519_client").file.toPath(),
            remoteHost = sftpContainer.host,
            port = sftpContainer.getMappedPort(SftpContainer.PORT),
            strictHostChecking = true
        )
                     */

//                    session.setPassword(access.password)
                    // val config = Properties()
                    // config["foo"] = "bar"
                    // session.setConfig(config)
                    session.userInfo =
                        object : UserInfo {
                            override fun getPassphrase(): String? {
                                println("getPassphrase")
                                return null
                            }

                            override fun getPassword(): String {
                                println("getPassword")
                                return access.password
                            }

                            override fun promptPassword(message: String?): Boolean {
                                println("promptPassword: [$message]")
                                return true
                            }

                            override fun promptPassphrase(message: String?): Boolean {
                                println("promptPassphrase: [$message]")
                                return false
                            }

                            override fun promptYesNo(message: String?): Boolean {
                                println("promptYesNo: [$message]")
                                if (message?.startsWith("The authenticity of host") ?: false) {
                                    // poor man's: jsch.setKnownHosts("/users/XXX/.ssh/known_hosts")
                                    return true
                                }
                                return false
                            }

                            override fun showMessage(message: String?) {
                                println("show message: [$message]")
                            }
                        }
                    session.setConfig("StrictHostKeyChecking", "no") // FIXME delete for prod! verify host keys properly
                    session.setConfig("HashKnownHosts", "yes")

//                jsch.addIdentity(privatekeyfile)
//                val session = jsch.getSession("user", "example.com", 22)

//                knownHostsFilePathString = resourceLoader.getResource("classpath:ssh/known_hosts").file.toPath().toString()

                    println("try connect")
                    session.connect(CONNECTION_TIMEOUT)
                    println("try open channel")
                    val channel =
                        session.openChannel(SFTP_CHANNEL)
                            ?: Failure
                                .ConnectionError(
                                    "Could not open channel to SFTP server at  ${access.host}:${access.port}",
                                ).left()
                                .bind()
                    println("try channel connect")
                    channel.connect(SESSION_TIMEOUT)
                    code((channel as ChannelSftp))
                    println("exit")
                    channel.exit()
                    Unit.right().bind()
                } catch (e: Exception) {
                    Failure
                        .ConnectionError("Failed to establish SFTP channel to ${access.host}:${access.port}", e)
                        .left()
                        .bind()
                } finally {
                    session?.disconnect()
                }
            }
    }
    fun upload(
        access: SftpAccess,
        source: File,
        targetPath: String,
    ): Either<Failure, Unit> =
        either {
            log.info { "upload($access, $source, $targetPath)" }
            withChannel(access) { channel ->
                channel.put(source.absolutePath, targetPath, ChannelSftp.OVERWRITE)
            }.bind()
        }
}
