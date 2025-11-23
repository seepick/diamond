package nl.uwv.smz.diamond.view.controller_impl

import arrow.core.Either
import arrow.core.raise.either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.domain_failure.mapToBadRequest
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto

class CrystalControllerImpl(private val service: CrystalService) : CrystalController {
    override fun findAll() = either {
        service.findAll().bind().map { it.toDto() }
    }

    override fun findSingle(crystalId: String): Either<Failure, CrystalDto> = either {
        service.findSingle(CrystalId(crystalId).mapToBadRequest().bind()).bind().toDto()
    }

    override fun create(create: CrystalCreateDto): Either<Failure, CrystalDto> = either {
        service.create(create.toDomain().bind()).bind().toDto()
    }

    override fun delete(crystalId: String): Either<Failure, Unit> = either {
        service.delete(CrystalId(crystalId).mapToBadRequest().bind()).bind()
    }
}

/** Translates bad data into 400 bad request */
private fun CrystalCreateDto.toDomain() = either {
    CrystalCreate(
        weight = Gram(weightInGram).mapToBadRequest().bind()
    )
}

private fun Crystal.toDto() = CrystalDto(
    id = id.value.toString(),
    weightInGram = weight.value,
)
