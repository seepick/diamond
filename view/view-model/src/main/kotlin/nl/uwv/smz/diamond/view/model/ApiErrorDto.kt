package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import nl.uwv.smz.diamond.domain_failure.ErrorCode

@Serializable
data class ApiErrorDto(
    /** To guarantee/enforce a determined set of values (useful for FE to react) */
    @Serializable(with = ErrorCodeSerializer::class)
    val code: ErrorCode,
    val message: String,
)

object ErrorCodeSerializer : KSerializer<ErrorCode> {
    // Serial names of descriptors should be unique, this is why we advise including app package in the name.
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("diamond.ErrorCode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ErrorCode) {
        encoder.encodeString(value.renderedValue)
    }

    override fun deserialize(decoder: Decoder): ErrorCode {
        val string = decoder.decodeString()
        return ErrorCode.byRenderedValueOrThrow(string)
    }
}
