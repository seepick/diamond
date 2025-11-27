package nl.uwv.smz.diamond.extern.impl

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.spec.tempfile

class SftpImplTest : StringSpec({
    val sftpServer = SftpListener()
    extension(sftpServer)
    "fo" {
        val file = tempfile(suffix = ".txt")
        println("sftpServer.access: ${sftpServer.access}")
        SftpImpl().upload(sftpServer.access, file, "/copied.txt").shouldBeRight()

        SftpImpl.withChannel(sftpServer.access) { channel ->
            channel.ls("/")
        }
    }
})

class SftpListener : BeforeSpecListener, AfterSpecListener {
    private var sftpServer: SftpContainer? = null
    val access get() = sftpServer!!.access

    override suspend fun beforeSpec(spec: Spec) {
        sftpServer =
            SftpContainer().apply {
                start()
            }
    }

    override suspend fun afterSpec(spec: Spec) {
        sftpServer?.stop()
    }
}
