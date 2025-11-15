# Kotlin/Native Table-TUI

# About
Create simple tables effortlessly using the DSL.

```kotlin
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
```

outputs

```
┌────┬──────┬─────┐
│ ID │ Name │ Age │
├────┼──────┼─────┤
│ 1  │ John │ 20  │
├────┼──────┼─────┤
│ 2  │ Jane │ 22  │
├────┼──────┼─────┤
│ 3  │ Doe  │ 25  │
└────┴──────┴─────┘
```