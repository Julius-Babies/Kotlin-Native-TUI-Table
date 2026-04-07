import es.jvbabi.tui.table.buildTable
import es.jvbabi.tui.table.components.BorderStyle
import util.buildStyledString

fun main() {
    val table = buildTable {
        border = BorderStyle.Borderless
        row {
            cell { +buildStyledString { bold { +"ID" } } }
            cell { +"Details" }
            cell { +"Status" }
        }

        row {
            cell { +"1" }
            cell {
                colspan = 2
                centered = true
                +buildStyledString { gray { italic { +"No data available" } } }
            }
        }

        row {
            cell { +"2" }
            cell { +"Item A" }
            cell {
                centered = true
                +"✓"
            }
        }
    }

    println(table)
}