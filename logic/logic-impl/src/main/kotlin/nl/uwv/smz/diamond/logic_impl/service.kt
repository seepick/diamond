package nl.uwv.smz.diamond.logic_impl

import nl.uwv.smz.diamond.logic_api.Service

class ServiceImpl : Service {
    override fun greet(): String = "Hello Service!"
}
