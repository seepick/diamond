package nl.uwv.smz.diamond.itest.testInfra

import io.cucumber.java.BeforeAll
import nl.uwv.smz.diamond.shared.test.reconfigureLogForTest

/** The global kotest project configuration doesn't apply, as cucumber has its own approach similar to JUnit. */
@Suppress("unused") // via cucumber annotation hook
object LogHook {
    @BeforeAll // must be cucumber's, not the one from junit!
    @JvmStatic
    fun reconfigureLog() {
        reconfigureLogForTest()
    }
}
