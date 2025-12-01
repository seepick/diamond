package nl.uwv.smz.diamond.domain.model

enum class CrystalSortField(override val queryName: String) : SortField {
    Id("id"),
    WeightInGram("weightInGram"),
    ;

    companion object Find : SortFieldFinder<CrystalSortField>(entries)
}

data class CrystalSortRequest(
    override val field: CrystalSortField,
    override val direction: SortDirection,
) : SortRequest<CrystalSortField>

data class CrystalSortingsRequest(
    override val sorts: List<CrystalSortRequest>
) : SortingsRequest<CrystalSortRequest, CrystalSortField> {
    companion object // for extensions
    // TODO check for duplicates; contradicting like (+id, -id), but also (+id, +id)
}
