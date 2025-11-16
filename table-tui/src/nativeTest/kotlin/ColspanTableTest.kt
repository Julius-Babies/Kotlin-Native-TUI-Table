import es.jvbabi.tui.table.buildTable
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

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
                cell {
                    +"Address"
                }
            }

            row {
                cell { +"1" }
                cell {
                    colspan = 2
                    +"John"
                }
                cell { +"20" }
            }
        }

        table shouldBe """┌────┬──────┬─────┬─────────┐
│ ID │ Name │ Age │ Address │
├────┼──────┴─────┼─────────┤
│ 1  │ John       │ 20      │
└────┴────────────┴─────────┘"""
    }

    test("Last row spanned") {
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

        table shouldBe """┌────┬──────┬─────┐
│ ID │ Name │ Age │
├────┼──────┴─────┤
│ 1  │ John       │
└────┴────────────┘"""
    }
})