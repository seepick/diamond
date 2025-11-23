package nl.uwv.smz.diamond.view.routing

import nl.uwv.smz.diamond.shared.test.reconfigureLogForTest
import org.junit.jupiter.api.BeforeAll

object LogHook {
    @BeforeAll
    @JvmStatic
    fun reconfigureLog() {
        println("foo")
        reconfigureLogForTest()
    }
}
