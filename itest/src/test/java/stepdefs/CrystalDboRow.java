package stepdefs;

import com.deblock.cucumber.datatable.annotations.Column;
import com.deblock.cucumber.datatable.annotations.DataTableWithHeader;

@DataTableWithHeader
public record CrystalDboRow(
        @Column int weight
) {
}
