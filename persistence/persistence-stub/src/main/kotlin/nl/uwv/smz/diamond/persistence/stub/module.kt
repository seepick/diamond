package nl.uwv.smz.diamond.persistence.stub

import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.dsl.module

private val log = io.github.oshai.kotlinlogging.KotlinLogging.logger {}

@Suppress("UnusedReceiverParameter")
fun Modules.persistenceStub() = module {
    log.info { "Using persistence stub (no real impl will be used)" }
    single<CrystalRepo> { CrystalStubbedRepo() }
}
