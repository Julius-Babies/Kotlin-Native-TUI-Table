import es.jvbabi.tui.table.buildTable
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EmptyTableTest : FunSpec({
    test("empty table") {
        val table = buildTable {}

        table shouldBe ""
    }
})