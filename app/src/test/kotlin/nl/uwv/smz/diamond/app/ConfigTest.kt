package nl.uwv.smz.diamond.app

import io.kotest.core.spec.style.StringSpec

class ConfigTest : StringSpec({
    // TODO write test with environment variables set (this is how it is ultimately done)
    "asdf" {
        val config = readConfig()
        println(config)
    }
})
