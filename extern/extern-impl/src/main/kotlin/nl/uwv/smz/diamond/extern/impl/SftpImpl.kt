package nl.uwv.smz.diamond.extern.impl

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import nl.uwv.smz.diamond.domainFailure.Failure
import java.io.File

data class SftpAccess(
    val host: String,
    val port: Int = 22,
    val username: String,
    val password: String,
)

class SftpImpl {
    companion object {
        private const val CONNECTION_TIMEOUT = 10_000
        private const val SESSION_TIMEOUT = 5_000
        private const val SFTP_CHANNEL = "sftp"

        // VisibleForTesting (reuse/assert)
        @Suppress("TooGenericExceptionCaught")
        internal fun withChannel(
            access: SftpAccess,
            code: (ChannelSftp) -> Unit,
        ): Either<Failure, Unit> =
            either {
                var session: Session? = null
                try {
                    val jsch = JSch()
                    // jsch.setKnownHosts("/home/xxx/.ssh/known_hosts")
                    session = jsch.getSession(access.username, access.host, access.port)
                    session.setPassword(access.password)
                    // val config = Properties()
                    // config["foo"] = "bar"
                    // jschSession.setConfig(config)
//                jsch.addIdentity(privatekeyfile)
//                val session = jsch.getSession("user", "example.com", 22)
                    session.setConfig("StrictHostKeyChecking", "no") // FIXME delete for prod! verify host keys properly

                    session.connect(CONNECTION_TIMEOUT)
                    val channel =
                        session.openChannel(SFTP_CHANNEL)
                            ?: Failure
                                .ConnectionError("Could not open channel to SFTP server at  ${access.host}:${access.port}")
                                .left()
                                .bind()
                    channel.connect(SESSION_TIMEOUT)
                    code((channel as ChannelSftp))
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
            withChannel(access) { channel ->
                channel.put(source.absolutePath, targetPath, ChannelSftp.OVERWRITE)
            }.bind()
        }
}
