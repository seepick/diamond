package nl.uwv.smz.diamond.shared.config

//|===
//|Param |Type |Description
//
//|`FOO_BAR`
//|type
//|description
//
//|===
// TODO group by path; yaml like maybe
fun configPrinterAsAsciidoc(entries: List<ConfigEntry>): String {
    val sb = StringBuilder()
    sb.appendLine("|===")
    sb.append("|Param |Type |Default |Description")
    entries
        .sortedBy { it.path.joinToString() }
        .map { sb.appendEntry(it) }
    sb.appendLine().appendLine().appendLine("|===")
    return sb.toString()
    // TODO navigational nodes are NOT properties!
}

private fun StringBuilder.appendEntry(entry: ConfigEntry) {
    appendLine().appendLine()
    append("|`").appendPath(entry.path).append("`").appendLine()
    append("|").append(entry.type.label).appendLine()
    append("|").append(entry.default ?: "-").appendLine()
    append("|").append(entry.description)
}

private fun StringBuilder.appendPath(path: List<String>) = apply {
    append(path.joinToString("_") { it.uppercase() })
}
