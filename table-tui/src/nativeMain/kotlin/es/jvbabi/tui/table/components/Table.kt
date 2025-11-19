package es.jvbabi.tui.table.components

class Table internal constructor() {

    private val rows = mutableListOf<Row>()
    var cellPadding = 1

    var border: BorderStyle = BorderStyle.Default

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
                    // Put the deficit into the first column of the span for simplicity and speed.
                    colWidths[cIndex] += contentLen - currentSpanWidth
                }
                cIndex += span
            }
        }

        // Helper lambdas for drawing lines
        fun StringBuilder.drawBorder(left: Char, joint: Char, right: Char, horizontal: Char) {
            append(left)
            for (i in 0 until columnCount) {
                val segmentWidth = colWidths[i] + 2 * cellPadding
                repeat(segmentWidth) { append(horizontal) }
                append(if (i == columnCount - 1) right else joint)
            }
        }

        // Precompute, for each content row, where vertical borders exist at column boundaries.
        // A mask has size columnCount + 1 representing boundaries: 0 (left edge) ... columnCount (right edge).
        val verticalMasks: List<BooleanArray> = buildList(normalizedRows.size) {
            normalizedRows.forEach { row ->
                val mask = BooleanArray(columnCount + 1)
                var boundary = 0
                mask[boundary] = true // left edge is always a vertical border
                row.forEach { cell ->
                    boundary += maxOf(1, cell.colspan)
                    // Vertical border at the end of the spanned cell
                    mask[boundary] = true
                }
                add(mask)
            }
        }

        fun StringBuilder.drawAdaptiveSeparator(upperMask: BooleanArray, lowerMask: BooleanArray, border: BorderStyle.WithBorders) {
            // left edge
            append(border.leftJoint)
            // segments and joints between them
            for (i in 0 until columnCount) {
                val segmentWidth = colWidths[i] + 2 * cellPadding
                repeat(segmentWidth) { append(border.horizontal) }
                if (i == columnCount - 1) {
                    append(border.rightJoint)
                } else {
                    val boundaryIndex = i + 1 // boundary between column i and i+1
                    val up = upperMask[boundaryIndex]
                    val down = lowerMask[boundaryIndex]
                    val joint = when {
                        up && down -> border.middleJoint
                        up && !down -> border.bottomJoint
                        !up && down -> border.topJoint
                        else -> border.horizontal // continue horizontal if neither side has a vertical
                    }
                    append(joint)
                }
            }
        }

        // Bottom border that adapts to the verticals present in the last content row.
        // Where the last row has a vertical at a boundary, we draw a bottomJoint (┴),
        // otherwise we continue the horizontal line (─) through that boundary.
        fun StringBuilder.drawAdaptiveBottom(lastRowMask: BooleanArray, border: BorderStyle.WithBorders) {
            append(border.bottomLeft)
            for (i in 0 until columnCount) {
                val segmentWidth = colWidths[i] + 2 * cellPadding
                repeat(segmentWidth) { append(border.horizontal) }
                if (i == columnCount - 1) {
                    append(border.bottomRight)
                } else {
                    val boundaryIndex = i + 1
                    val hasVerticalAbove = lastRowMask[boundaryIndex]
                    val joint = if (hasVerticalAbove) border.bottomJoint else border.horizontal
                    append(joint)
                }
            }
        }

        return buildString {
            // Top border
            (border as? BorderStyle.WithBorders)?.let { border ->
                drawBorder(border.topLeft, border.topJoint, border.topRight, border.horizontal)
                appendLine()
            }

            // Rows
            normalizedRows.forEachIndexed { rowIndex, row ->
                (border as? BorderStyle.WithBorders)?.let { border -> append(border.vertical) }
                var cIndex = 0
                row.forEach { cell ->
                    val span = maxOf(1, cell.colspan)
                    // Inner width of the spanned cell should cover:
                    // - the inner widths of each spanned column (colWidth + 2*padding)
                    // - plus the widths of the skipped internal vertical separators (span - 1)
                    val innerByColumns = (0 until span).sumOf { colWidths[cIndex + it] + 2 * cellPadding }
                    val skippedSeparators = (span - 1)
                    val spanInnerWidth = innerByColumns + skippedSeparators

                    // Berechne linke/rechte Auffüllung je nach Ausrichtung
                    val baseLeftPad = cellPadding
                    val baseRightPad = cellPadding
                    val freeSpace = spanInnerWidth - cell.content.length - baseLeftPad - baseRightPad
                    if (cell.centered) {
                        val extraLeft = maxOf(0, freeSpace / 2)
                        val extraRight = maxOf(0, freeSpace - extraLeft)
                        repeat(baseLeftPad + extraLeft) { append(' ') }
                        append(cell.content)
                        repeat(baseRightPad + extraRight) { append(' ') }
                    } else {
                        // Standard: links nur Padding, rest nach rechts
                        repeat(baseLeftPad) { append(' ') }
                        append(cell.content)
                        val remaining = baseRightPad + maxOf(0, freeSpace)
                        repeat(remaining) { append(' ') }
                    }
                    // vertical border at the end of the span
                    (border as? BorderStyle.WithBorders)?.let { border ->
                        append(border.vertical)
                    }
                    cIndex += span
                }

                    if (rowIndex < normalizedRows.lastIndex) {
                        appendLine()
                        (border as? BorderStyle.WithBorders)?.let { border ->
                            // Adaptive separator considering verticals in rows above/below (handles colspans)
                            val upperMask = verticalMasks[rowIndex]
                            val lowerMask = verticalMasks[rowIndex + 1]
                            drawAdaptiveSeparator(upperMask, lowerMask, border)
                            appendLine()
                        }
                    }
            }

            // Bottom border (adaptive to last row's vertical boundaries)
            (border as? BorderStyle.WithBorders)?.let { border ->
                appendLine()
                val lastMask = verticalMasks.last()
                drawAdaptiveBottom(lastMask, border)
            }
        }
    }
}