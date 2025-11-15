import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SimpleTableTest : FunSpec({
    test("simple table") {
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

            people.forEach {
                row {
                    cell {
                        +it.id
                    }

                    cell {
                        +it.name
                    }

                    cell {
                        +it.age.toString()
                    }
                }
            }
        }

        table shouldBe """┌────┬──────┬─────┐
│ ID │ Name │ Age │
├────┼──────┼─────┤
│ 1  │ John │ 20  │
├────┼──────┼─────┤
│ 2  │ Jane │ 22  │
├────┼──────┼─────┤
│ 3  │ Doe  │ 25  │
└────┴──────┴─────┘"""
    }
})

data class Person(val id: String, val name: String, val age: Int)
val people = listOf(Person("1", "John", 20), Person("2", "Jane", 22), Person("3", "Doe", 25))