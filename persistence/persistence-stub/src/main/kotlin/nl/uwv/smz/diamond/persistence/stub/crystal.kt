package nl.uwv.smz.diamond.persistence.stub

import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.persistence.api.CrystalDbo
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import kotlin.uuid.Uuid

internal class CrystalStubbedRepo : CrystalRepo {

    private val crystals = mutableListOf<CrystalDbo>()

    override fun loadAll() =
        crystals

    override fun findById(id: CrystalId) =
        crystals.firstOrNull { it.id == id.value }?.right() ?: Failure.NotFoundFailure("").left()

    override fun create(create: CrystalCreate) =
        CrystalDbo(
            id = Uuid.random(),
            weightInGram = create.weight.value,
        ).also {
            crystals += it
        }.right()

    override fun update(update: CrystalUpdate) = either {
        val dbo = findById(update.id).bind() // will cancel with NotFoundFailure if not found
        crystals.remove(dbo)
        dbo.updateBy(update).also {
            crystals += it
        }
    }

    override fun delete(id: CrystalId) =
        if (crystals.removeIf { it.id == id.value }) Unit.right()
        else Failure.NotFoundFailure("Crystal not found with ID: $id").left()
}

private fun CrystalDbo.updateBy(update: CrystalUpdate) = copy(
    weightInGram = update.weight.value
)
