package nl.uwv.smz.diamond.persistence.impl

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldNotContain

class DbConfigTest : StringSpec({
    "Password not shown in toString" {
        DbConfig(url = "", username = "", password = "xxx").toString() shouldNotContain "xxx"
    }
})
