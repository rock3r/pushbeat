buildscript {
    dependencies {
        classpath("com.google.cloud.tools:appengine-gradle-plugin:2.2.0")
    }
}

plugins {
    application
    war
    kotlin("jvm")
}

apply(plugin = "com.google.cloud.tools.appengine")

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation(kotlin("stdlib"))

    val kotlinVersion = rootProject.extra["kotlin_version"]
    val ktorVersion = rootProject.extra["ktor_version"]

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.1")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")

    providedCompile("com.google.appengine:appengine:1.9.60")
}

tasks {
    named("run") {
        dependsOn(named("appengineRun"))
    }
}
