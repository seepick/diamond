package nl.uwv.smz.diamond.extern.impl

import nl.uwv.smz.diamond.extern.api.posts.PostsExtern
import nl.uwv.smz.diamond.extern.api.sftp.SftpConfig
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnector
import nl.uwv.smz.diamond.extern.impl.posts.PostsExternImpl
import nl.uwv.smz.diamond.extern.impl.sftp.SftpConnectorImpl
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.shared.config.ConfigProperty
import nl.uwv.smz.diamond.shared.config.SubConfig
import org.koin.dsl.module

data class ExternConfig(
    @ConfigProperty("Base URL for the external posts service")
    val postsServiceBaseUrl: String,
    @SubConfig
    val sftp: SftpConfig,
)

@Suppress("UnusedReceiverParameter")
fun Modules.externImpl(config: ExternConfig) =
    module {
        single<PostsExtern> { PostsExternImpl(baseUrl = config.postsServiceBaseUrl) }
        single<SftpConnector> { SftpConnectorImpl(config.sftp) }
    }
