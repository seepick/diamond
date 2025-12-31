package demoApp.db

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID
import kotlin.random.Random

object ItemTable : UUIDTable("ITEMS", "ID") {
    val name = text("NAME")
}

data class Item(val id: UUID, val name: String) {
    companion object {
        fun byRow(row: ResultRow) = Item(id = row[ItemTable.id].value, name = row[ItemTable.name])
    }
}

object ItemRepo {
    fun selectAll() = transaction {
        ItemTable.selectAll().map { Item.byRow(it) }
    }

    fun insertRandom() = transaction {
        ItemTable.insert {
            it[id] = UUID.randomUUID()
            it[name] = "Name ${Random.nextInt(0, 10_000)}"
        }
    }
}
