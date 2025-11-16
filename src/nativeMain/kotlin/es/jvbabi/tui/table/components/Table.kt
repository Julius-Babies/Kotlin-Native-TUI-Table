package es.jvbabi.tui.table.components

class Table internal constructor() {

    private val rows = mutableListOf<Row>()
    var cellPadding = 1

    data class BorderStyle(
        val topLeft: Char = '┌',
        val topRight: Char = '┐',
        val bottomLeft: Char = '└',
        val bottomRight: Char = '┘',
        val horizontal: Char = '─',
        val vertical: Char = '│',
        val topJoint: Char = '┬',
        val middleJoint: Char = '┼',
        val leftJoint: Char = '├',
        val rightJoint: Char = '┤',
        val bottomJoint: Char = '┴'
    )

    var border: BorderStyle = BorderStyle()

    fun row(block: Row.() -> Unit) {
        val row = Row()
        row.apply(block)
        rows.add(row)
    }

    override fun toString(): String {
        if (rows.isEmpty()) return ""
        // Precompute column count considering colspans
        val columnCount = rows.maxOf { r -> r.cells.sumOf { maxOf(1, it.colspan) } }

        // Normalize rows: ensure each row fills all columns by adding empty filler cell if needed
        val normalizedRows: List<List<Cell>> = rows.map { r ->
            val currentSpan = r.cells.sumOf { maxOf(1, it.colspan) }
            if (currentSpan == columnCount) r.cells
            else {
                val deficit = columnCount - currentSpan
                val filler = Cell().also { it.content = ""; it.colspan = deficit }
                r.cells + filler
            }
        }

        // Compute column widths (content only). Padding and borders accounted during rendering.
        val colWidths = IntArray(columnCount) { 0 }
        normalizedRows.forEach { row ->
            var cIndex = 0
            row.forEach { cell ->
                val span = maxOf(1, cell.colspan)
                val contentLen = cell.content.length
                val currentSpanWidth = (0 until span).sumOf { colWidths.getOrElse(cIndex + it) { 0 } }
                if (contentLen > currentSpanWidth) {
                    // Put deficit into the first column of the span for simplicity and speed.
                    colWidths[cIndex] += contentLen - currentSpanWidth
                }
                cIndex += span
            }
        }

        // Helper lambdas for drawing lines
        fun StringBuilder.drawBorder(left: Char, joint: Char, right: Char) {
            append(left)
            for (i in 0 until columnCount) {
                val segmentWidth = colWidths[i] + 2 * cellPadding
                repeat(segmentWidth) { append(border.horizontal) }
                append(if (i == columnCount - 1) right else joint)
            }
        }

        return buildString {
            // Top border
            drawBorder(border.topLeft, border.topJoint, border.topRight)
            appendLine()

            // Rows
            normalizedRows.forEachIndexed { rowIndex, row ->
                append(border.vertical)
                var cIndex = 0
                row.forEach { cell ->
                    val span = maxOf(1, cell.colspan)
                    val spanWidth = (0 until span).sumOf { colWidths[cIndex + it] } + 2 * cellPadding
                    // left padding
                    repeat(cellPadding) { append(' ') }
                    append(cell.content)
                    // right padding and remaining spaces inside the spanned area
                    val remaining = spanWidth - cellPadding - cell.content.length
                    repeat(remaining) { append(' ') }
                    // vertical border at the end of the span
                    append(border.vertical)
                    cIndex += span
                }

                if (rowIndex < normalizedRows.lastIndex) {
                    appendLine()
                    // Separator
                    drawBorder(border.leftJoint, border.middleJoint, border.rightJoint)
                    appendLine()
                }
            }

            // Bottom border
            appendLine()
            drawBorder(border.bottomLeft, border.bottomJoint, border.bottomRight)
        }
    }
}