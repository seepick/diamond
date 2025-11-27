package nl.uwv.smz.diamond.extern.impl.delme

import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session

interface ConnectionStrategy {
    fun build(knownHostsFilePath: String): JSch = JSch().also { it.setKnownHosts(knownHostsFilePath) }
}

class SftpPasswordConnectionStrategyImpl(
    private val username: String,
    private val password: String,
    private val remoteHost: String,
    private val port: Int?,
    private val strictHostChecking: Boolean = true,
) : ConnectionStrategy {
    fun connect(knownHostsFilePath: String): Session =
        build(knownHostsFilePath)
            .run {
                port?.let { getSession(username, remoteHost, it) } ?: getSession(username, remoteHost)
            }.also {
                it.setPassword(password)
                it.setConfig("StrictHostKeyChecking", if (strictHostChecking) "yes" else "no")
                it.setConfig("HashKnownHosts", "yes")
                it.connect()
            }
}

class SftpPrivateKeyConnectionStrategyImpl(
    private val username: String,
    private val privateKey: String,
    private val remoteHost: String,
    private val port: Int?,
    private val strictHostChecking: Boolean = true,
) : ConnectionStrategy {
    fun connect(knownHostsFilePath: String): Session =
        build(knownHostsFilePath)
            .apply { addIdentity(privateKey) }
            .run {
                port?.let { getSession(username, remoteHost, it) } ?: getSession(username, remoteHost)
            }.also {
                it.setConfig("StrictHostKeyChecking", if (strictHostChecking) "yes" else "no")
                it.setConfig("HashKnownHosts", "yes")
                it.connect()
            }
}
