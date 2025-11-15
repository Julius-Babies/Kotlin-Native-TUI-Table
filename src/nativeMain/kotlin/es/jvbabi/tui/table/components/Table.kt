package es.jvbabi.tui.table.components

class Table internal constructor() {

    private val rows = mutableListOf<Row>()
    var cellPadding = 1

    fun row(block: Row.() -> Unit) {
        val row = Row()
        row.apply(block)
        rows.add(row)
    }

    override fun toString(): String {
        if (rows.isEmpty()) return ""
        return buildString {
            rows.forEachIndexed { rowIndex, row ->
                // Start char
                when (rowIndex) {
                    0 -> append("┌")
                    else -> append("├")
                }

                // Overline
                row.cells.forEachIndexed { cellIndex, _ ->
                    val longestLength = rows.maxOf { it.cells.getOrNull(cellIndex)?.content?.length ?: 0 }
                    repeat(longestLength + 2*cellPadding) {
                        append("─")
                    }

                    if (rowIndex == 0) {
                        if (cellIndex == row.cells.lastIndex) append("┐")
                        else append("┬")
                    } else {
                        if (cellIndex == row.cells.lastIndex) append("┤")
                        else append("┼")
                    }
                }

                // Content
                appendLine()
                append("│")
                row.cells.forEachIndexed { cellIndex, cell ->
                    repeat(cellPadding) { append(" ") }
                    append(cell.content)
                    val longestLength = rows.maxOf { it.cells.getOrNull(cellIndex)?.content?.length ?: 0 }
                    repeat(longestLength - cell.content.length + cellPadding) {
                        append(" ")
                    }

                    append("│")
                }

                if (rowIndex < rows.lastIndex) appendLine()
            }

            // Bottom
            appendLine()
            append("└")
            rows.last().cells.forEachIndexed { cellIndex, _ ->
                val longestLength = rows.maxOf { it.cells.getOrNull(cellIndex)?.content?.length ?: 0 }
                repeat(longestLength + 2*cellPadding) {
                    append("─")
                }

                if (cellIndex == rows.last().cells.lastIndex) append("┘")
                else append("┴")
            }
        }
    }
}