import es.jvbabi.tui.table.buildTable
import es.jvbabi.tui.table.components.BorderStyle

fun main() {
    val table = buildTable {
        border = BorderStyle.Borderless
        row {
            cell { +"ID" }
            cell { +"Details" }
            cell { +"Status" }
        }

        row {
            cell { +"1" }
            cell {
                colspan = 2
                centered = true
                +"No data available"
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