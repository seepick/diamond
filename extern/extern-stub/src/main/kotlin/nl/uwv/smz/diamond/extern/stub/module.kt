package nl.uwv.smz.diamond.extern.stub

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.extern.api.posts.PostsExtern
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnector
import nl.uwv.smz.diamond.extern.stub.posts.PostsExternStub
import nl.uwv.smz.diamond.extern.stub.sftp.SftpConnectorStub
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.dsl.module

private val log = logger {}

fun Modules.externStub() =
    module {
        log.info { "Using extern stub (no real impl will be used)" }
        single<PostsExtern> { PostsExternStub() }
        single<SftpConnector> { SftpConnectorStub() }
    }
