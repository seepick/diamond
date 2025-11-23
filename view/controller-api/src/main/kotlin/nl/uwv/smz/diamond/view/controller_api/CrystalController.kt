package nl.uwv.smz.diamond.view.controller_api

import nl.uwv.smz.diamond.view.model.CrystalDto

interface CrystalController {
    fun getAll(): List<CrystalDto>
}
