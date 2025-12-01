package nl.uwv.smz.diamond.persistence.impl

import nl.uwv.smz.diamond.domain.model.SortDirection
import org.jetbrains.exposed.sql.SortOrder

val SortDirection.exposedOrder: SortOrder
    get() = when (this) {
        SortDirection.Asc -> SortOrder.ASC_NULLS_LAST
        SortDirection.Desc -> SortOrder.DESC_NULLS_LAST
    }
