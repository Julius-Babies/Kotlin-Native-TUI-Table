plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinxSerialization)

    alias(libs.plugins.kotest)
    alias(libs.plugins.ksp)

    alias(libs.plugins.maven.publish)
}

group = "io.github.julius-babies"
version = System.getenv("VERSION")?.ifBlank { null } ?: "unspecified"

repositories {
    mavenCentral()
}

kotlin {

    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()
    mingwX64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        nativeMain.dependencies {
            implementation(libs.kotlinxSerializationJson)
        }

        nativeTest.dependencies {
            implementation(libs.kotest.framework.engine)
            implementation(libs.kotest.assertions.core)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(project.group.toString(), project.name, project.version.toString())

    pom {
        name = "table-tui"
        description = "A simple tui table library for Kotlin/Native"
        url = "https://github.com/Julius-Babies/Kotlin-Native-TUI-Table"

        developers {
            developer {
                id = "julius-vincent-babies"
                name = "Julius Vincent Babies"
                email = "julvin.babies@gmail.com"
                url = "https://github.com/Julius-Babies"
            }
        }

        scm {
            url = "https://github.com/Julius-Babies/Kotlin-Native-TUI-Table"
        }

        licenses {
            license {
                name = "The MIT License (MIT)"
                url = "https://opensource.org/license/MIT"
            }
        }
    }
}