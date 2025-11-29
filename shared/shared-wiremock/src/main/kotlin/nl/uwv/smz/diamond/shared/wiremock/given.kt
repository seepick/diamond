package nl.uwv.smz.diamond.shared.wiremock

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.http.HttpHeader
import com.github.tomakehurst.wiremock.http.HttpHeaders
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder

// TODO rewrite as DSL
@Suppress("LongParameterList")
fun givenWiremock(
    method: WiremockMethod = WiremockMethod.GET,
    path: String = "/",
    statusCode: Int = 200,
    responseBody: String? = null,
    withResponse: ResponseDefinitionBuilder.() -> Unit = {}
) {
    stubFor(
        method.stubForPath(path).willReturn(
            aResponse()
                .withStatus(statusCode)
                .withBody(responseBody)
                .apply { withResponse(this) })
    )
}

fun ResponseDefinitionBuilder.withHeaders(vararg headers: Pair<String, String>) = apply {
    withHeaders(HttpHeaders(headers.map { HttpHeader(it.first, it.second) }))
}

fun Request.hasHeader(pair: Pair<String, String>) =
    headers.getHeader(pair.first).containsValue(pair.second)

fun RequestPatternBuilder.withHeader(key: String, expectedValue: String) = apply {
    withHeader(key, equalTo(expectedValue))
}

fun RequestPatternBuilder.withRequestBody(bytes: ByteArray) = apply {
    withRequestBody(equalTo(String(bytes)))
}

fun RequestPatternBuilder.withCookie(key: String, expectedValue: String) = apply {
    withCookie(key, equalTo(expectedValue))
}
