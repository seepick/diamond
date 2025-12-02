package nl.uwv.smz.diamond.view.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import kotlinx.serialization.json.Json
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

class CrystalDtoTest : StringSpec({
    "serializes" {
        var dto = Arb.crystalDto().next()
        JSONAssert.assertEquals(
            """{"id": "${dto.id}", "weightInGram": ${dto.weightInGram}, "created": "${dto.created}"}""",
            Json.encodeToString(dto),
            JSONCompareMode.STRICT,
        )
    }
})
