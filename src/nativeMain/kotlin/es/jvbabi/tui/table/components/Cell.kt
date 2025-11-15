package es.jvbabi.tui.table.components

class Cell internal constructor() {
    var content: String = ""

    operator fun String.unaryPlus() {
        content += this
    }
}