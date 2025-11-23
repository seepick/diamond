package nl.uwv.smz.diamond.view.controller_api

import arrow.core.Either
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto

interface CrystalController {
    fun findAll(): List<CrystalDto>
    fun findSingle(crystalId: String): Either<Failure, CrystalDto>
    fun create(create: CrystalCreateDto): Either<Failure, CrystalDto>
    fun delete(crystalId: String): Either<Failure, Unit>
}
