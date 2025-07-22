plugins {
    kotlin("multiplatform") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
}

group = "com.lignting"
version = "1.0-SNAPSHOT"

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            val kotlinWrappersVersion = "2025.7.9"
            
            dependencies {
                //React, React DOM + Wrappers
                implementation(project.dependencies.enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
                
                //Kotlin React Emotion (CSS)
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion-css")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion-react")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion-utils")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion-cache")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion-styled")
                
                //Coroutines & serialization
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
                
                implementation(kotlin("stdlib-js"))
            }
            
            this.dependsOn.forEach { dependency ->
                println("Dependency $dependency")
            }
        }
    }
}

// Heroku Deployment
tasks.register("stage") {
    dependsOn("build")
}

// Disable Yarn download in the root project (to avoid downloading Yarn during build)
//rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
//    val root = rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>()
//    root.download = false
//}

// Print JS dependencies (for debugging purposes)
tasks.register("printJsDependencies") {
    doLast {
        val jsMainRuntimeClasspath = project.configurations.findByName("jsRuntimeClasspath")
        jsMainRuntimeClasspath?.resolvedConfiguration?.resolvedArtifacts?.forEach {
            println("Loaded dependency: ${it.moduleVersion.id}")
        }
    }
}