package nl.uwv.smz.diamond.persistence.impl

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import kotlinx.coroutines.Dispatchers
import nl.uwv.smz.diamond.domain.failure.Failure
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.uuid.Uuid

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

fun DatabaseConfig.toDatabaseAccess() = DatabaseAccess(
    jdbcUrl = jdbcUrl,
    username = username,
    password = password.value,
)

internal suspend fun <T> suspendTransaction(db: Database, block: Transaction.() -> T): T =
    newSuspendedTransaction(
        context = Dispatchers.IO,
        db = db,
        statement = block,
    )

internal fun <T> List<T>.ensureSingleFound(id: Uuid): Either<Failure, T> = either {
    when (size) {
        0 -> Failure.NotFoundFailure("Not found: $id").left()
        1 -> first().right()
        else -> Failure.CorruptDataFailure("Duplicate entries found for ID: $id").left()
    }.bind()
}

fun <RETURN> ensureSingleAffected(affectedRows: Int, id: Uuid, returnValue: () -> RETURN) = either {
    when (affectedRows) {
        0 -> Failure.NotFoundFailure("Not any found with ID: $id").left()
        1 -> returnValue().right()
        else -> Failure.CorruptDataFailure("More than 1 entity found with same ID: $id").left()
    }.bind()
}
