package nl.uwv.smz.diamond.persistence.impl

import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.persistence.api.CrystalDbo
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.uuid.toKotlinUuid

/*
val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )

transaction(database) {

    suspend fun read(id: Int): ExposedUser? {
        return dbQuery {
            Users.selectAll()
                .where { Users.id eq id }
                .map { ExposedUser(it[Users.name], it[Users.age]) }
                .singleOrNull()
        }
    }


    suspend fun update(id: Int, user: ExposedUser) {
        dbQuery {
            Users.update({ Users.id eq id }) {
                it[name] = user.name
                it[age] = user.age
            }
        }
    }
 */
object CrystalTable : UUIDTable("CRYSTALS", "ID") {
    val weightInGrams = integer("WEIGHT_IN_GRAMS")
}

// FIXME also try org.jetbrains.exposed:exposed-dao
// https://ktor.io/docs/server-integrate-database.html#create-mapping
//class CrystalDao(id: EntityID<UUID>) : org.jetbrains.exposed.dao.(id) {
//    companion object : IntEntityClass<CrystalDao>(CrystalTable)
//
//    var weightInGrams by CrystalTable.weightInGrams
//}

private fun CrystalDbo.Companion.byRow(row: ResultRow) = CrystalDbo(
    id = row[CrystalTable.id].value.toKotlinUuid(),
    weightInGram = row[CrystalTable.weightInGrams],
)

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

internal object CrystalExposedRepo : CrystalRepo {
    override suspend fun selectAll(): List<CrystalDbo> = suspendTransaction {
        CrystalTable.selectAll().map { row ->
            CrystalDbo.byRow(row)
        }
    }

    override fun selectById(id: CrystalId): Either<Failure, CrystalDbo> {
        TODO("Not yet implemented")
    }

    override fun create(create: CrystalCreate): Either<Failure, CrystalDbo> {
        TODO("Not yet implemented")
    }

    override fun update(update: CrystalUpdate): Either<Failure, CrystalDbo> {
        TODO("Not yet implemented")
    }

    override fun delete(id: CrystalId): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}
