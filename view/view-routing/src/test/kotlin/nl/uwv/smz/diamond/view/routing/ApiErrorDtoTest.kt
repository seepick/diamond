package nl.uwv.smz.diamond.view.routing

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.serialization.json.Json
import nl.uwv.smz.diamond.domainFailure.ErrorCode
import nl.uwv.smz.diamond.view.model.ApiErrorDto

class ApiErrorDtoTest : StringSpec({
    val code = ErrorCode.BAD_CLIENT_REQUEST
    val message = "test message"
    val dto = ApiErrorDto(code, message)
    val dtoAsString = """{"code":"${code.renderedValue}","message":"$message"}"""

    "When serialize DTO Then use rendered value property instead of Kotlin identifier name" {
        Json.encodeToString(dto) shouldBeEqual dtoAsString
    }
    "When deserialize json Then construct DTO" {
        Json.decodeFromString<ApiErrorDto>(dtoAsString) shouldBeEqual dto
    }
})
