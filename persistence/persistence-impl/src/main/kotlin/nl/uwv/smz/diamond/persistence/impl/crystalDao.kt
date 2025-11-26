package nl.uwv.smz.diamond.persistence.impl

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domainFailure.Failure
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import java.util.UUID
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

// https://ktor.io/docs/server-integrate-database.html#create-mapping
internal class CrystalDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CrystalDao>(CrystalTable)

    var weightInGrams by CrystalTable.weightInGrams
}

internal class CrystalExposedDaoRepo(private val db: Database) : CrystalRepo {

    override suspend fun selectAll() = either {
        suspendTransaction(db) {
            CrystalDao.all().map { it.toDomainModel().bind() }
        }
    }

    override suspend fun selectById(id: CrystalId) = either {
        suspendTransaction(db) {
            CrystalDao
                .find { (CrystalTable.id eq id.value.toJavaUuid()) }
                .map { it.toDomainModel().bind() }
                .ensureSingleFound(id.value).bind()
        }
    }

    override suspend fun insert(create: CrystalCreate): Either<Failure, Crystal> = either {
        suspendTransaction(db) {
            CrystalDao.new(UUID.randomUUID()) {
                weightInGrams = create.weight.value
            }.toDomainModel().bind()
        }
    }

    override suspend fun update(update: CrystalUpdate): Either<Failure, Crystal> = either {
        suspendTransaction(db) {
            val found = CrystalDao.findByIdAndUpdate(update.id.value.toJavaUuid()) {
                it.weightInGrams = update.weight.value
            } ?: Failure.NotFoundFailure("Not found for update: ${update.id}").left().bind()

            found.toDomainModel().bind().right().bind()
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


private fun CrystalDao.toDomainModel() = either {
    Crystal(
        id = CrystalId(id.value.toKotlinUuid()),
        weight = Gram(weightInGrams).bind()
    )
}
