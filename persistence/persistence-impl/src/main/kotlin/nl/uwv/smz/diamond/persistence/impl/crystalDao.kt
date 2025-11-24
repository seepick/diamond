package nl.uwv.smz.diamond.persistence.impl

import arrow.core.Either
import arrow.core.raise.either
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

    override suspend fun create(create: CrystalCreate): Either<Failure, Crystal> {
        TODO("Not yet implemented")
    }

    override suspend fun update(update: CrystalUpdate): Either<Failure, Crystal> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: CrystalId): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }

}
/*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere


    override suspend fun taskByName(name: String): Task? = suspendTransaction {
        TaskDAO
            .find { (TaskTable.name eq name) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun addTask(task: Task): Unit = suspendTransaction {
        TaskDAO.new {
            name = task.name
            description = task.description
            priority = task.priority.toString()
        }
    }

    override suspend fun removeTask(name: String): Boolean = suspendTransaction {
        val rowsDeleted = TaskTable.deleteWhere {
            TaskTable.name eq name
        }
        rowsDeleted == 1
    }
 */


private fun CrystalDao.toDomainModel() = either {
    Crystal(
        id = CrystalId(id.value.toKotlinUuid()),
        weight = Gram(weightInGrams).bind()
    )
}
