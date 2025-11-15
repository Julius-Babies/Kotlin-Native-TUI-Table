package es.jvbabi.tui.table.components

class Row internal constructor() {
    val cells = mutableListOf<Cell>()

    fun cell(block: Cell.() -> Unit) {
        val cell = Cell()
        cell.apply(block)
        cells.add(cell)
    }
}