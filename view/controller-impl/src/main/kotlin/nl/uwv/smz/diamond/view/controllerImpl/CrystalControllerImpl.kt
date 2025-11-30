package nl.uwv.smz.diamond.view.controllerImpl

import arrow.core.raise.either
import nl.uwv.smz.diamond.domain.failure.mapLeftToBadRequest
import nl.uwv.smz.diamond.domain.logicApi.CrystalService
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.view.controllerApi.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto
import nl.uwv.smz.diamond.view.model.CrystalUpdateDto
import nl.uwv.smz.diamond.view.model.PageRequestDto

class CrystalControllerImpl(private val service: CrystalService) : CrystalController {

    override suspend fun findAll(pageRequest: PageRequestDto) = either {
        service.findAll(pageRequest.toPageRequest().bind()).bind().toDto { it.toDto() }
    }

    override suspend fun findSingle(crystalId: String) = either {
        service.findSingle(CrystalId(crystalId).mapLeftToBadRequest().bind()).bind().toDto()
    }

    override suspend fun create(create: CrystalCreateDto) = either {
        service.create(create.toDomain().bind()).bind().toDto()
    }

    override suspend fun update(crystalId: String, update: CrystalUpdateDto) = either {
        service.update(
            CrystalUpdate(
                id = CrystalId(crystalId).bind(),
                weight = Gram(update.weightInGram).bind(),
            ),
        ).map { it.toDto() }.bind()
    }

    override suspend fun delete(crystalId: String) = either {
        service.delete(CrystalId(crystalId).mapLeftToBadRequest().bind()).bind()
    }
}

/** Translates bad data into 400 bad request */
private fun CrystalCreateDto.toDomain() = either {
    CrystalCreate(
        weight = Gram(weightInGram).mapLeftToBadRequest().bind(),
    )
}

private fun Crystal.toDto() = CrystalDto(
    id = id.value,
    weightInGram = weight.value,
)
