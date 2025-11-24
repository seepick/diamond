package nl.uwv.smz.diamond.persistence.impl

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import kotlinx.coroutines.Dispatchers
import nl.uwv.smz.diamond.domainFailure.Failure
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.uuid.Uuid

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

fun <RETURN> ensureSingleUpdate(updatedRows: Int, id: Uuid, returnValue: () -> RETURN) = either {
    when (updatedRows) {
        0 -> Failure.NotFoundFailure("Not found for update: $id").left()
        1 -> returnValue().right()
        else -> Failure.CorruptDataFailure("More than 1 entities with same ID found: $id").left()
    }.bind()
}
