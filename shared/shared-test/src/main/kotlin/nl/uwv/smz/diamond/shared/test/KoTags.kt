package nl.uwv.smz.diamond.shared.test

import io.kotest.core.NamedTag

/**
 * Tags for kotest as strings to be used by annotations (@RequiresTag).
 * The typesafe approach (extending Tag) only allows for marking a test, but not requiring a specific tag.
 */
@Suppress("ConstPropertyName")
object KoTags {
    const val testcontainersName = "testcontainers" // can be used in annotations via @RequiresTag(..)
    val testcontainersTag = NamedTag(testcontainersName) // can be used in code via tags(..)
}
