package nl.uwv.smz.diamond.shared.common.kaml.github.dsl

import nl.uwv.smz.diamond.shared.common.kaml.github.domain.Permission
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.PermissionLevel
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.PermissionType

@GithubDsl
class PermissionsDsl {
    var contents: PermissionLevel? = null
    // add more...

    fun build() = mapOf(
        PermissionType.Contents to contents,
    ).mapNotNull { entry ->
        entry.value?.let { value ->
            Permission(entry.key, value)
        }
    }
}
