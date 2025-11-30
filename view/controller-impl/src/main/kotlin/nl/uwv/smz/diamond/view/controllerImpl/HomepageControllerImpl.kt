package nl.uwv.smz.diamond.view.controllerImpl

import nl.uwv.smz.diamond.domain.logicApi.GreetService
import nl.uwv.smz.diamond.view.controllerApi.HomepageController

internal class HomepageControllerImpl(private val greetService: GreetService) : HomepageController {
    override fun greet() = greetService.greet()
}
