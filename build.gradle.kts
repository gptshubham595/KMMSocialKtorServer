
val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val h2_version: String by project
val exposed_version: String by project
val koin_version: String by project
val hikaricp_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("example.com.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("com.h2database:h2:2.1.214")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.0.0")

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("io.insert-koin:koin-ktor:3.4.0")

    implementation("de.mkammerer.snowflake-id:snowflake-id:0.0.2")
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")

    implementation("io.github.smiley4:ktor-swagger-ui:2.2.0")
    implementation("io.ktor:ktor-server-openapi:2.3.12")
}
