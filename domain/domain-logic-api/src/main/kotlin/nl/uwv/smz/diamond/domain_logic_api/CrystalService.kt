package nl.uwv.smz.diamond.domain_logic_api

import arrow.core.Either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domainFailure.Failure

interface CrystalService {
    /** Failure if for example corrupt data in database. */
    suspend fun findAll(): Either<Failure, List<Crystal>>

    suspend fun findSingle(id: CrystalId): Either<Failure, Crystal>

    suspend fun create(create: CrystalCreate): Either<Failure, Crystal>

    suspend fun update(update: CrystalUpdate): Either<Failure, Crystal>

    suspend fun delete(id: CrystalId): Either<Failure, Unit>
}
