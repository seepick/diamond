package nl.uwv.smz.diamond.persistence.stub

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domainFailure.Failure
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import kotlin.uuid.Uuid

internal class CrystalStubbedRepo : CrystalRepo {

    private val crystals = mutableListOf<Crystal>()

    override suspend fun selectAll(): Either<Failure, List<Crystal>> = either {
        crystals
    }

    override suspend fun selectById(id: CrystalId): Either<Failure, Crystal> = either {
        (crystals.firstOrNull { it.id == id }?.right()
            ?: Failure.NotFoundFailure("").left()).bind()
    }

    override suspend fun create(create: CrystalCreate): Either<Failure, Crystal> = either {
        Crystal(
            id = CrystalId(Uuid.random()),
            weight = create.weight,
        ).also {
            crystals += it
        }.right().bind()
    }

    override suspend fun update(update: CrystalUpdate): Either<Failure, Crystal> = either {
        val crystal = selectById(update.id).bind() // will cancel with NotFoundFailure if not found
        crystals.remove(crystal)
        crystal.updateBy(update).also {
            crystals += it
        }
    }

    override suspend fun delete(id: CrystalId): Either<Failure, Unit> = either {
        (if (crystals.removeIf { it.id == id }) Unit.right()
        else Failure.NotFoundFailure("Crystal not found with ID: $id").left()).bind()
    }
}

private fun Crystal.updateBy(update: CrystalUpdate) = copy(
    weight = update.weight
)
