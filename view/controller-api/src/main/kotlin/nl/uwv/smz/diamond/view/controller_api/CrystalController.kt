package nl.uwv.smz.diamond.view.controller_api

import arrow.core.Either
import nl.uwv.smz.diamond.domainFailure.Failure
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto
import nl.uwv.smz.diamond.view.model.CrystalUpdateDto

interface CrystalController {
    suspend fun findAll(): Either<Failure, List<CrystalDto>>
    suspend fun findSingle(crystalId: String): Either<Failure, CrystalDto>
    suspend fun create(create: CrystalCreateDto): Either<Failure, CrystalDto>
    suspend fun update(crystalId: String, update: CrystalUpdateDto): Either<Failure, CrystalDto>
    suspend fun delete(crystalId: String): Either<Failure, Unit>
}
