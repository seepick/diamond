package nl.uwv.smz.diamond.itest

import io.cucumber.java.BeforeAll
import nl.uwv.smz.diamond.shared.test.reconfigureLogForTest

// https://www.baeldung.com/java-cucumber-hooks
// https://towardsdev.com/how-to-perfectly-test-your-ktor-application-94fb92c5a303
@Suppress("unused") // via cucumber annotation hook
object LogHook {
    @BeforeAll // must be cucumber's, not the one from junit!
    @JvmStatic
    fun reconfigureLog() {
        println("itest - LogHook")
        reconfigureLogForTest()
    }
}
