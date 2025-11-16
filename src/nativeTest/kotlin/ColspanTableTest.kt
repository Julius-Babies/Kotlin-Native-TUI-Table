import es.jvbabi.tui.table.buildTable
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.test.logging.info

@OptIn(ExperimentalKotest::class)
class ColspanTableTest : FunSpec({
    test("Center spanned") {
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

        info { table }
    }
}) {
}