# Kotlin/Native Table-TUI

A lightweight, intuitive DSL for creating beautiful text-based tables in Kotlin/Native. Perfect for CLI applications, logs, and terminal UIs.

## Quick Start

Create a simple table using the DSL:

```kotlin
val table = buildTable {
    row {
        cell { +"ID" }
        cell { +"Name" }
        cell { +"Age" }
    }
    
    row {
        cell { +"1" }
        cell { +"John" }
        cell { +"20" }
    }
    
    row {
        cell { +"2" }
        cell { +"Jane" }
        cell { +"22" }
    }
}

println(table)
```

**Output:**
```
┌────┬──────┬─────┐
│ ID │ Name │ Age │
├────┼──────┼─────┤
│ 1  │ John │ 20  │
├────┼──────┼─────┤
│ 2  │ Jane │ 22  │
└────┴──────┴─────┘
```

## Getting Started

### Installation

Add the library to your project using Gradle:

#### Gradle (Kotlin DSL)
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.julius-babies:table-tui:§§VERSIONTAG§§")
}
```

#### Version Catalog
```toml
[versions]
table-tui = "§§VERSIONTAG§§"

[libraries]
table-tui = { module = "io.github.julius-babies:table-tui", version.ref = "table-tui" }
```

```kotlin
dependencies {
    implementation(libs.table.tui)
}
```

### Basic Usage

Import the `buildTable` function and start creating tables:

```kotlin
val table = buildTable {
    row {
        cell { +"Hello" }
        cell { +"World" }
    }
}
```

## Features

<details>
<summary><b>Basic Tables</b></summary>

Create simple tables with rows and cells using the intuitive DSL.

```kotlin
val table = buildTable {
    row {
        cell { +"ID" }
        cell { +"Name" }
        cell { +"Age" }
    }
    
    people.forEach { person ->
        row {
            cell { +person.id }
            cell { +person.name }
            cell { +person.age.toString() }
        }
    }
}

println(table)
```

**Output:**
```
┌────┬──────┬─────┐
│ ID │ Name │ Age │
├────┼──────┼─────┤
│ 1  │ John │ 20  │
├────┼──────┼─────┤
│ 2  │ Jane │ 22  │
└────┴──────┴─────┘
```

</details>

<details>
<summary><b>Custom Cell Padding</b></summary>

Adjust the spacing around cell content to improve readability.

```kotlin
val table = buildTable {
    cellPadding = 2  // Default is 1
    
    row {
        cell { +"ID" }
        cell { +"Name" }
        cell { +"Age" }
    }
    
    row {
        cell { +"1" }
        cell { +"John" }
        cell { +"20" }
    }
}

println(table)
```

**Output:**
```
┌──────┬────────┬───────┐
│  ID  │  Name  │  Age  │
├──────┼────────┼───────┤
│  1   │  John  │  20   │
└──────┴────────┴───────┘
```

</details>

<details>
<summary><b>Column Spanning (Colspan)</b></summary>

Merge multiple columns into a single cell using `colspan`.

```kotlin
val table = buildTable {
    row {
        cell { +"ID" }
        cell { +"Name" }
        cell { +"Age" }
        cell { +"Address" }
    }
    
    row {
        cell { +"1" }
        cell {
            colspan = 2  // This cell spans 2 columns
            +"John Doe"
        }
        cell { +"New York" }
    }
}

println(table)
```

**Output:**
```
┌────┬───────┬─────┬──────────┐
│ ID │ Name  │ Age │ Address  │
├────┼───────┴─────┼──────────┤
│ 1  │ John Doe    │ New York │
└────┴─────────────┴──────────┘
```

</details>

<details>
<summary><b>Cell Alignment</b></summary>

Center content within cells using the `centered` property.

```kotlin
val table = buildTable {
    row {
        cell { +"ID" }
        cell { +"Name" }
        cell { +"Status" }
    }
    
    row {
        cell { +"1" }
        cell { +"John" }
        cell {
            centered = true
            +"Active"
        }
    }
}

println(table)
```

**Output:**
```
┌────┬──────┬────────┐
│ ID │ Name │ Status │
├────┼──────┼────────┤
│ 1  │ John │ Active │
└────┴──────┴────────┘
```

</details>

<details>
<summary><b>Border Styles</b></summary>

Choose from predefined border styles: `Default`, `Double`, or `Borderless`.

```kotlin
val table = buildTable {
    // Use a predefined style
    border = BorderStyle.Double
    
    row {
        cell { +"ID" }
        cell { +"Name" }
    }
    
    row {
        cell { +"1" }
        cell { +"John" }
    }
}

println(table)
```

**Output:**
```
╔════╦══════╗
║ ID ║ Name ║
╠════╬══════╣
║ 1  ║ John ║
╚════╩══════╝
```

You can also hide all borders:

```kotlin
val table = buildTable {
    border = BorderStyle.Borderless
    row { cell { +"Hello" }; cell { +"World" } }
}

println(table) // prints just the cell contents separated by spaces
```

</details>

<details>
<summary><b>Combining Features</b></summary>

Mix and match features for complex table layouts.

```kotlin
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
```

**Output:**
```
┌──────┬───────────────┬──────────┐
│  ID  │  Details      │  Status  │
├──────┼───────────────┴──────────┤
│  1   │    No data available     │
├──────┼───────────────┬──────────┤
│  2   │  Item A       │    ✓     │
└──────┴───────────────┴──────────┘
```

</details>

<details>
<summary><b>Empty Tables</b></summary>

Empty tables are handled gracefully and return an empty string.

```kotlin
val table = buildTable {
    // No rows added
}

println(table)  // Prints nothing
println(table == "")  // true
```

</details>

## Platform Support

This library supports the following Kotlin/Native targets:

- Linux (x64, ARM64)
- macOS (x64, ARM64)
- Windows (x64)

## API Reference

### Table DSL

- `buildTable { ... }` - Creates a table and returns its string representation
- `cellPadding: Int` - Sets the padding around cell content (default: 1)
- `border: BorderStyle` - Selects the border style (`Default`, `Double`, `Borderless`)

### Row DSL

- `row { ... }` - Adds a new row to the table

### Cell DSL

- `cell { ... }` - Adds a new cell to the current row
- `+"content"` - Adds content to the cell using the unary plus operator
- `colspan: Int` - Number of columns the cell should span (default: 1)
- `centered: Boolean` - Centers the content within the cell (default: false)

## License

This project is licensed under the MIT License.