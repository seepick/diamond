package stepdefs;

import com.deblock.cucumber.datatable.annotations.Column;
import com.deblock.cucumber.datatable.annotations.DataTableWithHeader;

// needs to be in java, as it requires an empty constructor, etc... :-/
@DataTableWithHeader
public record PostDtoRow(@Column int id, @Column String title) {
}
