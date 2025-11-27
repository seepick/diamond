package stepdefs;

import com.deblock.cucumber.datatable.annotations.Column;
import com.deblock.cucumber.datatable.annotations.DataTableWithHeader;

@DataTableWithHeader
public record PostRtoRow(
        @Column int id,
        @Column int userId,
        @Column String title,
        @Column(mandatory = false) Boolean completed
) {
}
