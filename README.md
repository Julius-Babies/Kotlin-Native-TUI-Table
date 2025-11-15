# Kotlin/Native Table-TUI

# About
Create simple tables effortlessly using the DSL.

```kotlin
val table = buildTable {
    row {
        cell { +"ID" }
        cell { +"Name" }
        cell { +"Age" }
    }

    people.forEach {
        row {
            cell { +it.id }
            cell { +it.name }
            cell { +it.age.toString() }
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

# Getting Started
## Add the library to your project
### Gradle
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.julius-babies:table-tui:v0.0.2")
}

```

### Version Catalog
```toml
[versions]
table-tui = "v0.0.2"

[libraries]
table-tui = { module = "io.github.julius-babies:table-tui", version.ref = "table-tui" }
```

```kotlin
implementation(libs.table.tui)
```

## Create a table
See above.