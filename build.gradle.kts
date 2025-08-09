import kotlin.collections.map

val TheCupVersion by extra { "0.0.1" }

allprojects {
    group = "com.lignting"
    version = TheCupVersion
}

plugins {
    id("idea")
}

idea {
    module {
        excludeDirs.addAll(
            listOf(
                "app/appMain/node_modules", "api/build", "buildSrc/build", ".gradle", "dist"
            ).map { file(it) }
        )
    }
}