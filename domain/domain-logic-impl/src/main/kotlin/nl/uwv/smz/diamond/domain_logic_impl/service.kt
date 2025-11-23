package nl.uwv.smz.diamond.domain_logic_impl

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.domain_logic_api.GreetService
import kotlin.uuid.Uuid

class GreetServiceImpl : GreetService {
    override fun greet(): String = "Hello Service!"
}

class CrystalServiceImpl : CrystalService {

    private val log = logger {}

    override fun loadAll(): List<Crystal> {
        log.debug { "loadAll()" }
        return listOf(Crystal(id = CrystalId(Uuid.random()), weight = Gram(1337)))
    }
}
