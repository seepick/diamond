package nl.uwv.smz.diamond.persistence.stub

import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.dsl.module

@Suppress("UnusedReceiverParameter")
fun Modules.persistenceStub() = module {
    single<CrystalRepo> { CrystalStubbedRepo() }
}
