package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.When
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlinx.coroutines.runBlocking
import nl.uwv.smz.diamond.itest.World

class HomepageStepDefs(private val world: World) {

    private val log = logger {}

    // TODO Given service returns "bar" ... rewire koin objects for tests with test module

    @When("get home page")
    fun `When get home page`(): Unit = runBlocking {
        log.info { "Artificial wait for 500ms" }
        world.getHomepage()
    }

}
