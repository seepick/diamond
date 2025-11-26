package nl.uwv.smz.diamond.persistence.impl

import com.sksamuel.hoplite.Masked
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldNotContain

class DatabaseConfigTest : StringSpec({
    "Password not shown in toString" {
        DatabaseConfig(jdbcUrl = "", username = "", password = Masked("xxx")).toString() shouldNotContain "xxx"
    }
})

class DatabaseAccessTest : StringSpec({
    "Password not shown in toString" {
        DatabaseAccess(jdbcUrl = "", username = "", password = "xxx").toString() shouldNotContain "xxx"
    }
})
