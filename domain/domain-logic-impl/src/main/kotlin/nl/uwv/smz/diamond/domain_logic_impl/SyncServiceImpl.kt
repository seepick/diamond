package nl.uwv.smz.diamond.domain_logic_impl

import arrow.core.raise.either
import arrow.core.right
import io.github.oshai.kotlinlogging.KotlinLogging
import nl.uwv.smz.diamond.domain_logic_api.SyncService
import nl.uwv.smz.diamond.extern.api.sftp.SftpConnector
import kotlin.io.path.Path

class SyncServiceImpl(
    private val connector: SftpConnector,
//    private val crystalService: CrystalService,
) : SyncService {

    private val log = KotlinLogging.logger {}

    // TODO use cronjob to execute it
    override suspend fun sync() =
        either {
            log.info { "sync" }
            connector.connect().use { sftp ->
                val files = sftp.listRemoteFiles(Path("/upload"))
                log.debug { "Remote upload folder contains ${files.size} files." }
                files.forEach { file ->
                    log.debug { "Processing remote file: $file" }
//                  sftp.downloadFile(it, Path("sftp_download/${it.fileName}"))
//                  crystalService.create(CrystalCreate(Gram(23).bind()))
                }
            }
            Unit.right().bind()
        }
}
