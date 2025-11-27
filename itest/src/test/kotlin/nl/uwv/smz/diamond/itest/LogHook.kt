package nl.uwv.smz.diamond.itest

import io.cucumber.java.BeforeAll
import nl.uwv.smz.diamond.shared.test.reconfigureLogForTest

@Suppress("unused") // via cucumber annotation hook
object LogHook {
    @BeforeAll // must be cucumber's, not the one from junit!
    @JvmStatic
    fun reconfigureLog() {
        reconfigureLogForTest()
    }
}
