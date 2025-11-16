import es.jvbabi.tui.table.buildTable

fun main() {
    val table = buildTable {
        row {
            cell {
                +"ID"
            }
            cell {
                +"Name"
            }
            cell {
                +"Age"
            }
        }

        row {
            cell { +"1" }
            cell {
                colspan = 2
                +"John"
            }
        }
    }

    println(table)
}