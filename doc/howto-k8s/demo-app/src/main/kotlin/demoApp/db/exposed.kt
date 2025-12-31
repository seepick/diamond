package demoApp.db

import org.jetbrains.exposed.sql.Database

data class DatabaseAccess(
    val jdbcUrl: String,
    val username: String,
    val password: String,
) {
    override fun toString() = "DatabaseAccess[jdbcUrl=$jdbcUrl; username=$username; password=****]"
}

fun Database.Companion.connect(access: DatabaseAccess) = connect(
    url = access.jdbcUrl,
    user = access.username,
    password = access.password,
)
