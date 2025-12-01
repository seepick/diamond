package nl.uwv.smz.diamond.domain.model

import io.kotest.assertions.arrow.core.shouldBeRight

fun PageRequest.Companion.default() =
    build(skip = 0, take = 10)

fun PageRequest.Companion.build(skip: Int, take: Int) =
    PageRequest(skip = skip, take = take).shouldBeRight()
