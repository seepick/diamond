package nl.uwv.smz.diamond.view.controller_impl

import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalDto

class CrystalControllerImpl(private val service: CrystalService) : CrystalController {
    override fun getAll(): List<CrystalDto> =
        service.loadAll().map {
            it.toDto()
        }
}

private fun Crystal.toDto() = CrystalDto(
    id = id.value.toString(),
    weightInGram = weight.value,
)
