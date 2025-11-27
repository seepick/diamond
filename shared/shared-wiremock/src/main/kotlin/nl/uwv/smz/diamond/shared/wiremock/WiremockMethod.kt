package nl.uwv.smz.diamond.shared.wiremock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.delete
import com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.patch
import com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.put
import com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.http.RequestMethod
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder

enum class WiremockMethod(val requestMethod: RequestMethod) {
    GET(RequestMethod.GET) {
        override fun stubForPath(path: String) = get(urlEqualTo(path))!!
        override fun requestedFor(path: String) = getRequestedFor(urlEqualTo(path))!!
    },
    POST(RequestMethod.POST) {
        override fun stubForPath(path: String) = post(urlEqualTo(path))!!
        override fun requestedFor(path: String) = postRequestedFor(urlEqualTo(path))!!
    },
    PUT(RequestMethod.PUT) {
        override fun stubForPath(path: String) = put(urlEqualTo(path))!!
        override fun requestedFor(path: String) = putRequestedFor(urlEqualTo(path))!!
    },
    DELETE(RequestMethod.DELETE) {
        override fun stubForPath(path: String) = delete(urlEqualTo(path))!!
        override fun requestedFor(path: String) = deleteRequestedFor(urlEqualTo(path))!!
    },
    PATCH(RequestMethod.PATCH) {
        override fun stubForPath(path: String) = patch(urlEqualTo(path))!!
        override fun requestedFor(path: String) = patchRequestedFor(urlEqualTo(path))!!
    }
    ;

    /** For preparing. */
    abstract fun stubForPath(path: String): MappingBuilder

    /** For verification. */
    abstract fun requestedFor(path: String): RequestPatternBuilder

}
