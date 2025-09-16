import org.gradle.accessors.dm.LibrariesForKotlinWrappers

plugins {
    kotlin("multiplatform") version "2.2.20"
}

val dependenciesList: LibrariesForKotlinWrappers.() -> List<Provider<MinimalExternalModuleDependency>> = {
    listOf(
        js,
        react,
        reactDom,
        emotion.css,
        emotion.react,
        reactRouter,
    )
}

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
            dependencies {
                dependenciesList(kotlinWrappers).forEach {
                    implementation(it)
                }
            }
        }
    }
}