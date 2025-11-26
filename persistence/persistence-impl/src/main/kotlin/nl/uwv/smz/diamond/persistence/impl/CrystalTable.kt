package nl.uwv.smz.diamond.persistence.impl

import org.jetbrains.exposed.dao.id.UUIDTable

object CrystalTable : UUIDTable("CRYSTALS", "ID") {
    val weightInGrams = integer("WEIGHT_IN_GRAMS")
}
