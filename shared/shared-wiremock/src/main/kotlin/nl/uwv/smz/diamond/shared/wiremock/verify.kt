package nl.uwv.smz.diamond.shared.wiremock

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder

fun verifyGetRequest(path: String, withRequest: RequestPatternBuilder.() -> Unit = {}) {
    verifyRequest(WiremockMethod.GET, path, withRequest)
}

fun verifyPostRequest(path: String, withRequest: RequestPatternBuilder.() -> Unit = {}) {
    verifyRequest(WiremockMethod.POST, path, withRequest)
}

fun verifyPutRequest(path: String, withRequest: RequestPatternBuilder.() -> Unit = {}) {
    verifyRequest(WiremockMethod.PUT, path, withRequest)
}

fun verifyDeleteRequest(path: String, withRequest: RequestPatternBuilder.() -> Unit = {}) {
    verifyRequest(WiremockMethod.DELETE, path, withRequest)
}

fun verifyRequest(
    method: WiremockMethod,
    path: String,
    withRequest: RequestPatternBuilder.() -> Unit = {}
) {
    val builder = method.requestedFor(path)
    withRequest(builder)
    WireMock.verify(1, builder)
}
