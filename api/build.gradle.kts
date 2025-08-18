val kotlin_version: String by project
val logback_version: String by project
val jimmer_version: String by project
val sqlite_version: String by project
val ktor_version: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.2.2"
    id("com.google.devtools.ksp") version "2.1.10-1.0.31"
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    // ktor dependencies
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-gson")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    testImplementation("io.ktor:ktor-server-test-host")
    
    // data management dependencies
    implementation("org.babyfish.jimmer:jimmer-sql-kotlin:$jimmer_version")
    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmer_version}")
    implementation("org.xerial:sqlite-jdbc:$sqlite_version")
    
    // kotlin test dependencies
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// Without this configuration, gradle command can still run.
// However, Intellij cannot find the generated source.
kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}