package nl.uwv.smz.diamond.view.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import kotlinx.serialization.json.Json
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import java.time.LocalDateTime

class CrystalDtoTest : StringSpec({
    "serializes" {
        var dto = Arb.crystalDto().next().let {
            it.copy(created = LocalDateTime.of(2000, 12, 31, 12, 13, 14))
        }
        JSONAssert.assertEquals(
            """{"id": "${dto.id}", "weightInGram": ${dto.weightInGram}, "created": "2000-12-31T12:13:14"}""",
            Json.encodeToString(dto),
            JSONCompareMode.STRICT,
        )
    }
})
