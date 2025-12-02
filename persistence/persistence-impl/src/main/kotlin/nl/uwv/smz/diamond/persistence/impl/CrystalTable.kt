package nl.uwv.smz.diamond.persistence.impl

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object CrystalTable : UUIDTable("CRYSTALS", "ID") {
    val created = datetime("CREATED")
    val weightInGrams = integer("WEIGHT_IN_GRAMS")
}
