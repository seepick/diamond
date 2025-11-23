package nl.uwv.smz.diamond.view.controller_impl

import arrow.core.Either
import arrow.core.raise.either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto

class CrystalControllerImpl(private val service: CrystalService) : CrystalController {
    override fun findAll(): List<CrystalDto> =
        service.findAll().map {
            it.toDto()
        }

    override fun findSingle(crystalId: String): Either<Failure, CrystalDto> = either {
        service.findSingle(CrystalId(crystalId).mapInvalidRequestFailure().bind()).bind().toDto()
    }

    override fun create(create: CrystalCreateDto): Either<Failure, CrystalDto> = either {
        service.create(create.toDomain()).bind().toDto()
    }

    override fun delete(crystalId: String): Either<Failure, Unit> = either {
        service.delete(CrystalId(crystalId).mapInvalidRequestFailure().bind()).bind()
    }

    private fun <X> Either<Failure.BadDataFailure, X>.mapInvalidRequestFailure(): Either<Failure.InvalidRequestFailure, X> =
        mapLeft { Failure.InvalidRequestFailure(it.message) }
}


private fun CrystalCreateDto.toDomain() = CrystalCreate(
    weight = Gram(weightInGram) // TODO here could do domain checks
)

private fun Crystal.toDto() = CrystalDto(
    id = id.value.toString(),
    weightInGram = weight.value,
)
