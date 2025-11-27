package nl.uwv.smz.diamond.app

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class KoinTest : StringSpec({
    "verify modules or throw" {
        module {
            includes(Modules.all(Arb.Companion.globalConfig().next()))
        }.verify()
    }
})
