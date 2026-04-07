package es.jvbabi.tui.table.components

class Cell internal constructor() {
    var content: String = ""
    var colspan: Int = 1
    var centered: Boolean = false

    operator fun String.unaryPlus() {
        content += this
    }

    fun length(): Int {
        // Remove ANSI escape sequences: ESC [ ... m (and other CSI sequences)
        val stripped = content
            .replace(Regex("\u001B\\[[0-9;]*[a-zA-Z]"), "")  // CSI sequences (colors etc.)
            .replace(Regex("\u001B[^\\[\\s]"),              "")  // Other ESC sequences
        return stripped.length
    }
}