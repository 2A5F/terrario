import java.net.URI

plugins {
    id("fabric-loom")
    `maven-publish`
    kotlin("jvm")
    scala
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

group = "co.volight"
version = "1.0-SNAPSHOT"

val minecraft_version: String by project
val yarn_mappings: String by project
val loader_version: String by project
val fabric_version: String by project
val fabric_kotlin_version: String by project
val scala_version: String by project
val fabric_scala_version: String by project
val modmenu_version: String by project
val rei_version: String by project
val wthit_version: String by project

println()
println("minecraft_version: $minecraft_version")
println("yarn_mappings: $yarn_mappings")
println("loader_version: $loader_version")
println("fabric_version: $fabric_version")
println("fabric_kotlin_version: $fabric_kotlin_version")
println("scala_version: $scala_version")
println("fabric_scala_version: $fabric_scala_version")
println("modmenu_version: $modmenu_version")
println("rei_version: $rei_version")
println("wthit_version: $wthit_version")
println()

repositories {
    mavenCentral()
    maven { url = URI("https://maven.terraformersmc.com") }
    maven { url = URI("https://maven.shedaniel.me") }
    maven { url = URI("https://maven.bai.lol") }
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings("net.fabricmc:yarn:$yarn_mappings:v2")
    modImplementation("net.fabricmc:fabric-loader:${loader_version}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")
    modImplementation( "net.fabricmc:fabric-language-kotlin:${fabric_kotlin_version}")
//    modImplementation( "net.fabricmc:fabric-language-scala:${fabric_scala_version}")

    modRuntimeOnly("com.terraformersmc:modmenu:${modmenu_version}")
//    modRuntimeOnly("me.shedaniel:RoughlyEnoughItems-api-fabric:${rei_version}")
//    modRuntimeOnly("mcp.mobius.waila:wthit-api:fabric-${wthit_version}")

//    modRuntimeOnly("mcp.mobius.waila:wthit:fabric-${wthit_version}")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(16)
}

java {
    withSourcesJar()
}

tasks {
    processResources {
        inputs.property("version", version)

        filesMatching("fabric.mod.json") {
            expand("version" to version)
        }

        exclude("**/*.psd")
    }
}
