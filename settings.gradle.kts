rootProject.name = "TheCups"

pluginManagement {
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.aliyun.com/repository/public") }
    }

    versionCatalogs {
        create("libs") {
            val kotlinVersion = "2.2.20"
            plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").version(kotlinVersion)
            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").version(kotlinVersion)

            val serializationVersion = "1.9.0"
            library("serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").version(
                serializationVersion
            )
        }
        create("kotlinWrappers") {
            val wrappersVersion = "2025.9.6"
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}

include(":app")
include(":api")
include(":doc")
include(":appKotlin")