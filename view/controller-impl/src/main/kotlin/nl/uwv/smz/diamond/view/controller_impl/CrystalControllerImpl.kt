package nl.uwv.smz.diamond.view.controller_impl

import arrow.core.raise.either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain_failure.mapToBadRequest
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalDto
import nl.uwv.smz.diamond.view.model.CrystalUpdateDto

class CrystalControllerImpl(private val service: CrystalService) : CrystalController {

    override suspend fun findAll() = either {
        service.findAll().bind().map { it.toDto() }
    }

    override fun findSingle(crystalId: String) = either {
        service.findSingle(CrystalId(crystalId).mapToBadRequest().bind()).bind().toDto()
    }

    override fun create(create: CrystalCreateDto) = either {
        service.create(create.toDomain().bind()).bind().toDto()
    }

    override fun update(crystalId: String, update: CrystalUpdateDto) = either {
        service.update(
            CrystalUpdate(
                id = CrystalId(crystalId).bind(),
                weight = Gram(update.weightInGram).bind()
            ),
        ).map { it.toDto() }.bind()
    }

    override fun delete(crystalId: String) = either {
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
