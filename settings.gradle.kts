pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "2.2.21" apply false // Remember to change the version in libs.version.toml as well
}


rootProject.name = "kotlin-native-table-tui"
include(":table-tui", ":samples")