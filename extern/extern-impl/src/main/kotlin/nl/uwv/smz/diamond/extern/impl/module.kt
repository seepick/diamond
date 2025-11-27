package nl.uwv.smz.diamond.extern.impl

import nl.uwv.smz.diamond.extern.impl.posts.PostsService
import nl.uwv.smz.diamond.extern.impl.posts.PostsServiceImpl
import nl.uwv.smz.diamond.extern.impl.sftp.SftpConnector
import nl.uwv.smz.diamond.extern.impl.sftp.SftpConnectorImpl
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.shared.config.ConfigProperty
import org.koin.dsl.module

data class ExternConfig(
    @ConfigProperty("Base URL for the external posts service")
    val postsServiceBaseUrl: String,
)

fun Modules.externImpl(config: ExternConfig) =
    module {
        single<PostsService> { PostsServiceImpl(baseUrl = config.postsServiceBaseUrl) }
        single<SftpConnector> { SftpConnectorImpl }
    }
