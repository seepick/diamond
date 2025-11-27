package nl.uwv.smz.diamond.shared.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.engine.test.TestResult

object WiremockListener : BeforeSpecListener, AfterSpecListener, AfterTestListener {

    private var port: Int = -1
    private var server: WireMockServer? = null
    val url get() = "http://localhost:$port"

    override suspend fun beforeSpec(spec: Spec) {
        server = WireMockServer(
            options()
                .dynamicPort() // support parallel test execution
        ).apply {
            start()
        }
        port = server!!.port()
        configureFor("localhost", port)
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        server?.resetAll()
    }

    override suspend fun afterSpec(spec: Spec) {
        server?.stop()
    }
}
