package nl.uwv.smz.diamond.persistence.impl

import arrow.core.Either
import arrow.core.raise.either
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.persistence.api.CrystalDbo
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.persistence.impl.CrystalTable.id
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

internal class CrystalExposedDboRepo(private val db: Database) : CrystalRepo {

    private val log = logger {}

    override suspend fun selectAll() = either {
        suspendTransaction(db) {
            log.debug { "select all" }
            CrystalTable.selectAll().map { row ->
                CrystalDbo.byRow(row).toCrystal().bind()
            }
        }
    }

    override suspend fun selectById(id: CrystalId): Either<Failure, Crystal> = either {
        suspendTransaction(db) {
            CrystalTable.selectAll()
                .where { CrystalTable.id eq id.value.toJavaUuid() }
                .map { CrystalDbo.byRow(it).toCrystal().bind() }
                .ensureSingle(id.value).bind()
        }
    }

    override suspend fun create(create: CrystalCreate): Either<Failure, Crystal> {
        TODO("Not yet implemented")
    }

    override suspend fun update(update: CrystalUpdate): Either<Failure, Crystal> {
        TODO("Not yet implemented")
    }

    /*
        suspend fun update(id: Int, user: ExposedUser) {
            dbQuery {
                Users.update({ Users.id eq id }) {
                    it[name] = user.name
                    it[age] = user.age
                }
            }
        }
     */
    override suspend fun delete(id: CrystalId): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}

private fun CrystalDbo.toCrystal() = either {
    Crystal(
        id = CrystalId(id),
        weight = Gram(weightInGram).bind()
    )
}

private fun CrystalDbo.Companion.byRow(row: ResultRow) = CrystalDbo(
    id = row[id].value.toKotlinUuid(),
    weightInGram = row[CrystalTable.weightInGrams],
)
