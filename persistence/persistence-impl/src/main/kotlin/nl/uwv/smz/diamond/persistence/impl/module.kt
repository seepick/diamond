package nl.uwv.smz.diamond.persistence.impl

import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.dsl.module

fun Modules.persistenceImpl() = module {
    single<CrystalRepo> { CrystalExposedRepo }
}

fun connectToDatabase() {
//    Database.connect("")
}
//Database.connect(
//"jdbc:postgresql://localhost:5432/ktor_tutorial_db",
//user = "postgres",
//password = "password"
//)
