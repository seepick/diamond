package nl.uwv.smz.diamond.itest

import io.github.oshai.kotlinlogging.KotlinLogging.logger

// TODO before all: reconfigureLogback {}

// https://www.baeldung.com/java-cucumber-hooks
// https://towardsdev.com/how-to-perfectly-test-your-ktor-application-94fb92c5a303
class HooksSample {

    private val log = logger {}

//    @Before
//    fun `before each scenario`(scenario: Scenario) {
//        log.trace { "before scenario: ${scenario.name}" }
//    }
//
//    @BeforeStep
//    fun `before every step`() {
//        log.trace { "before step" }
//    }
//
//    @After
//    fun `after each scenario`() {
//        log.trace { "after scenario" }
//    }

    // @AfterStep
}
