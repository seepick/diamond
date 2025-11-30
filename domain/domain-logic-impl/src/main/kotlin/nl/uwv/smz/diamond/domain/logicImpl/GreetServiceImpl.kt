package nl.uwv.smz.diamond.domain.logicImpl

import nl.uwv.smz.diamond.domain.logicApi.GreetService

class GreetServiceImpl : GreetService {
    override fun greet(): String = "Hello Service!"
}
