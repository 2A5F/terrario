pluginManagement {
    val loom_version: String by settings
    val kotlin_version: String by settings

    println("loom_version: $loom_version")
    println("kotlin_version: $kotlin_version")
    println()

    repositories {
        maven {
            name = "Fabric"
            url = java.net.URI("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
    plugins {
        id("fabric-loom") version loom_version
        kotlin("jvm") version kotlin_version
    }
}

rootProject.name = "terrario"
