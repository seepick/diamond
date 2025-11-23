package nl.uwv.smz.diamond.domain_logic_impl

import nl.uwv.smz.diamond.domain_logic_api.GreetService

class GreetServiceImpl : GreetService {
    override fun greet(): String = "Hello Service!"
}
