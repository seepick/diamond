package nl.uwv.smz.diamond.extern.impl.sftp

object SftpFiles {
    val privateKey = javaClass.getResource("/sftp_ssh/id_ed25519_client").file
    val knownHosts = javaClass.getResource("/sftp_ssh/known_hosts").file
    const val hostEdKey = "/sftp_ssh/ssh_host_ed25519_key"
    const val hostRsaKey = "/sftp_ssh/ssh_host_rsa_key"
    const val clientPub = "/sftp_ssh/id_ed25519_client.pub"
}
