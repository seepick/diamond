package nl.uwv.smz.diamond.persistence.impl

import arrow.core.raise.either
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalSortField
import nl.uwv.smz.diamond.domain.model.CrystalSortRequest
import nl.uwv.smz.diamond.domain.model.CrystalSortingsRequest
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.persistence.impl.CrystalTable.id
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import kotlin.uuid.toKotlinUuid

fun CrystalSortingsRequest.toOrder(): List<Pair<Expression<*>, SortOrder>> =
    sorts.map { it.toOrder() }

fun CrystalSortRequest.toOrder(): Pair<Expression<*>, SortOrder> =
    when (field) {
        CrystalSortField.Id -> id
        CrystalSortField.WeightInGram -> CrystalTable.weightInGrams
    } to direction.exposedOrder

// used by DSL and DAO, as DAO approach has no sophisticated abstraction for this :-/ but it has a nice count method :-)
fun selectCrystalsWith(
    pageRequest: PageRequest,
    sorting: CrystalSortingsRequest,
) = either {
    @Suppress("SpreadOperator") // unfortunately exposed doesn't support a List version of it :-/
    CrystalTable.selectAll().limit(pageRequest.take).orderBy(*sorting.toOrder().toTypedArray())
        .toList().map { it.toCrystal().bind() }
        .toPage(
            pageRequest = pageRequest,
            hasMore = CrystalDaoEntity.count() > pageRequest.skip + pageRequest.take,
        )
}

/** Just a handy synonym for [Crystal.Companion.byRow]. */
fun ResultRow.toCrystal() = Crystal.byRow(this)

fun Crystal.Companion.byRow(row: ResultRow) = either {
    Crystal(
        id = CrystalId(row[id].value.toKotlinUuid()),
        weight = Gram(row[CrystalTable.weightInGrams]).bind(),
    )
}
