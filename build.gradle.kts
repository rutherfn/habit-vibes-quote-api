
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.nciholas.rutherford.habit.vibes.quote"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.postgresql)
    implementation(libs.h2)
    implementation("io.ktor:ktor-server-swagger:2.3.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    // implementation(libs.ktor.swagger.ui)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation("com.jayway.jsonpath:json-path:2.9.0")
}
