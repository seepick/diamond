package nl.uwv.smz.diamond.persistence.impl

import arrow.core.raise.either
import arrow.core.right
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalSortingsRequest
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.shared.common.Clock
import nl.uwv.smz.diamond.shared.common.UuidGenerator
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import kotlin.uuid.toJavaUuid

internal class CrystalExposedDboRepo(
    private val db: Database,
    private val uuidGen: UuidGenerator,
    private val clock: Clock,
) : CrystalRepo {

    private val log = logger {}

    override suspend fun selectAll(pageRequest: PageRequest, sorting: CrystalSortingsRequest) = either {
        suspendTransaction(db) {
            log.debug { "selectAll($pageRequest, $sorting)" }
            selectCrystalsWith(pageRequest, sorting).bind()
        }
    }

    override suspend fun selectById(id: CrystalId) = either {
        suspendTransaction(db) {
            selectByIdBlocking(id).bind()
        }
    }

    private fun selectByIdBlocking(id: CrystalId) = either {
        CrystalTable.selectAll()
            .where { CrystalTable.id eq id.value.toJavaUuid() }
            .map { Crystal.byRow(it).bind() }
            .ensureSingleFound(id.value).bind()
    }

    override suspend fun insert(create: CrystalCreate) = either {
        suspendTransaction(db) {
            val crystal = Crystal(
                id = CrystalId(uuidGen.generate()),
                created = clock.now(),
                weight = create.weight,
            )
            CrystalTable.insert {
                it[id] = crystal.id.value.toJavaUuid()
                it[created] = crystal.created
                it[weightInGrams] = crystal.weight.value
            }
            crystal.right().bind()
        }
    }

    override suspend fun update(update: CrystalUpdate) = either {
        suspendTransaction(db) {
            val updatedRows = CrystalTable.update({ CrystalTable.id eq update.id.value.toJavaUuid() }) {
                it[weightInGrams] = update.weight.value
            }
            ensureSingleAffected(updatedRows, update.id.value) {}.bind()
            selectByIdBlocking(update.id).bind()
        }
    }

    override suspend fun delete(id: CrystalId) = either {
        suspendTransaction(db) {
            val deletedCount = CrystalTable.deleteWhere {
                CrystalTable.id eq id.value.toJavaUuid()
            }
            ensureSingleAffected(deletedCount, id.value) { }.bind()
        }
    }
}
