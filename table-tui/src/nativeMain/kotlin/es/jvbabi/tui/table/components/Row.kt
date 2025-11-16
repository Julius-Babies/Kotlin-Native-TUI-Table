package es.jvbabi.tui.table.components

class Row internal constructor() {
    val cells = mutableListOf<Cell>()

    fun cell(colspan: Int = 1, block: Cell.() -> Unit) {
        val cell = Cell()
        cell.colspan = maxOf(1, colspan)
        cell.apply(block)
        cells.add(cell)
    }
}