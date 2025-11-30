package nl.uwv.smz.diamond.view.controllerApi

import arrow.core.Either
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto
import nl.uwv.smz.diamond.view.model.CrystalUpdateDto
import nl.uwv.smz.diamond.view.model.PageDto
import nl.uwv.smz.diamond.view.model.PageRequestDto

interface CrystalController {
    suspend fun findAll(pageRequest: PageRequestDto): Either<Failure, PageDto<CrystalDto>>

    suspend fun findSingle(crystalId: String): Either<Failure, CrystalDto>

    suspend fun create(create: CrystalCreateDto): Either<Failure, CrystalDto>

    suspend fun update(crystalId: String, update: CrystalUpdateDto): Either<Failure, CrystalDto>

    suspend fun delete(crystalId: String): Either<Failure, Unit>
}
