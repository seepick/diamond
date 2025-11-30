package nl.uwv.smz.diamond.persistence.impl

import arrow.core.raise.either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.persistence.impl.CrystalTable.id
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import kotlin.uuid.toKotlinUuid

// used by DSL and DAO, as DAO approach has no sophisticated abstraction for this :-/ but it has a nice count method :-)
fun selectPagedCrystals(pageRequest: PageRequest) = either {
    CrystalTable.selectAll().limit(pageRequest.take)
        .toList().map { it.toCrystal().bind() }
        .toPage(pageRequest, hasMore = CrystalDaoEntity.count() > pageRequest.skip + pageRequest.take)
}

// synonym
fun ResultRow.toCrystal() = Crystal.byRow(this)

fun Crystal.Companion.byRow(row: ResultRow) = either {
    Crystal(
        id = CrystalId(row[id].value.toKotlinUuid()),
        weight = Gram(row[CrystalTable.weightInGrams]).bind(),
    )
}
