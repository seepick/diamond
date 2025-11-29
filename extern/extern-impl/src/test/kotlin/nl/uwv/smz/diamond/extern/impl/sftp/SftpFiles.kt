package nl.uwv.smz.diamond.extern.impl.sftp

object SftpFiles {
    // on client
    val knownHosts = javaClass.getResource("/sftp_ssh/known_hosts").file
    val clientPrivKey = javaClass.getResource("/sftp_ssh/id_ed25519_client").file

    // on server
    const val clientPubKey = "/sftp_ssh/id_ed25519_client.pub"
    const val hostEdKey = "/sftp_ssh/ssh_host_ed25519_key"
    const val hostRsaKey = "/sftp_ssh/ssh_host_rsa_key"
}
