package nl.uwv.smz.diamond.shared.common

import io.github.oshai.kotlinlogging.KotlinLogging.logger

object Constants {

    private val log = logger {}

    val ROOT_PACKAGE_NAME = Constants::class.qualifiedName!!.split(".").dropLast(3).joinToString(".")

    init {
        log.info { "Computed root package name: [$ROOT_PACKAGE_NAME]" }
    }
}
