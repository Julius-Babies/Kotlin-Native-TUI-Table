package es.jvbabi.tui.table

import es.jvbabi.tui.table.components.Table

@Suppress("unused")
fun buildTable(block: Table.() -> Unit): String {
    val builder = Table()
    builder.apply(block)
    return builder.toString()
}