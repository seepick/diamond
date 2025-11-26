package nl.uwv.smz.diamond.persistence.impl.testInfra

import io.kotest.core.extensions.Extension
import nl.uwv.smz.diamond.persistence.impl.CrystalTable
import org.jetbrains.exposed.sql.Database

interface DbListener : Extension {
    val db: Database
}

val allTables = arrayOf(
    CrystalTable
)
