package nl.uwv.smz.diamond.persistence.api

import arrow.core.Either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain_failure.Failure
import kotlin.uuid.Uuid

data class CrystalDbo(
    val id: Uuid,
    val weightInGram: Int,
) {
    companion object
}

interface CrystalRepo {
    suspend fun selectAll(): Either<Failure, List<Crystal>>
    suspend fun selectById(id: CrystalId): Either<Failure, Crystal>
    suspend fun create(create: CrystalCreate): Either<Failure, Crystal>
    suspend fun update(update: CrystalUpdate): Either<Failure, Crystal>
    suspend fun delete(id: CrystalId): Either<Failure, Unit>
}
