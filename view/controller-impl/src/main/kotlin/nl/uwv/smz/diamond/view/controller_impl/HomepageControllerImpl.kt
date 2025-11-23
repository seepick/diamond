package nl.uwv.smz.diamond.view.controller_impl

import nl.uwv.smz.diamond.domain_logic_api.GreetService
import nl.uwv.smz.diamond.view.controller_api.HomepageController

internal class HomepageControllerImpl(private val greetService: GreetService) : HomepageController {
    override fun greet() = greetService.greet()
}
