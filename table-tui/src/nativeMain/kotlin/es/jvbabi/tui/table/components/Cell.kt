package es.jvbabi.tui.table.components

class Cell internal constructor() {
    var content: String = ""
    var colspan: Int = 1

    operator fun String.unaryPlus() {
        content += this
    }
}