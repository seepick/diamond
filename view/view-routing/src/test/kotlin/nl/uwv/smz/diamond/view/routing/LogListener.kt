package nl.uwv.smz.diamond.view.routing

import io.kotest.core.listeners.BeforeProjectListener
import nl.uwv.smz.diamond.shared.test.reconfigureLogForTest

object LogListener : BeforeProjectListener {
    override suspend fun beforeProject() {
        // FIXME logging for kotest?!
        println("before xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
        reconfigureLogForTest()
    }
}
