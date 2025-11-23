package nl.uwv.smz.diamond.domain_logic_api

import nl.uwv.smz.diamond.domain.model.Crystal

interface GreetService {
    fun greet(): String
}

interface CrystalService {
    fun loadAll(): List<Crystal>
}
