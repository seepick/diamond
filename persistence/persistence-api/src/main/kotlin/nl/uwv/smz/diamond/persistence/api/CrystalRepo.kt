package nl.uwv.smz.diamond.persistence.api

import arrow.core.Either
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain_failure.Failure
import kotlin.uuid.Uuid

data class CrystalDbo(
    val id: Uuid,
    val weightInGram: Int,
)

interface CrystalRepo {
    fun loadAll(): List<CrystalDbo>
    fun findById(id: CrystalId): Either<Failure, CrystalDbo>
    fun create(create: CrystalCreate): Either<Failure, CrystalDbo>
    fun delete(id: CrystalId): Either<Failure, Unit>
    // TODO implement update
}
