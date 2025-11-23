package nl.uwv.smz.diamond.view.controller_impl

import arrow.core.Either
import arrow.core.raise.either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalDto
import kotlin.uuid.Uuid

class CrystalControllerImpl(private val service: CrystalService) : CrystalController {
    override fun findAll(): List<CrystalDto> =
        service.findAll().map {
            it.toDto()
        }

    override fun findSingle(crystalId: String): Either<Failure, CrystalDto> = either {
        // TODO IllegalArgumentException parse UUID (test first)
        service.findSingle(CrystalId(Uuid.parse(crystalId))).bind().toDto()
    }

}

private fun Crystal.toDto() = CrystalDto(
    id = id.value.toString(),
    weightInGram = weight.value,
)
