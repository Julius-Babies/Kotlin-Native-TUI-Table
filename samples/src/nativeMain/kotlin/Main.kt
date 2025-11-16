import es.jvbabi.tui.table.buildTable
import es.jvbabi.tui.table.components.Table

fun main() {
    val table = buildTable {
        cellPadding = 2

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