import com.google.cloud.tools.gradle.appengine.standard.AppEngineStandardExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.3.72"
    war
}

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.google.cloud.tools:appengine-gradle-plugin:2.4.5")
    }
}

// Note: the AppEngine plugin currently does something funky when you apply it using the
// plugins DSL, so we need to use the old-school way for it to be able to work
apply(plugin = "com.google.cloud.tools.appengine")

configure<AppEngineStandardExtension> {
    deploy {
        projectId = "GCLOUD_CONFIG"
        version = "GCLOUD_CONFIG"
    }
}

repositories {
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    val kotlinVersion = rootProject.extra["kotlin_version"]
    val ktorVersion = rootProject.extra["ktor_version"]

    implementation(kotlin("stdlib-jdk8"))

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")

    implementation("io.ktor:ktor-server-servlet:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")

    providedCompile("com.google.appengine:appengine:1.9.60")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

tasks {
    @Suppress("UNUSED_VARIABLE") // Used by Gradle
    val run by registering {
        dependsOn(named("appengineRun"))
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
        }
    }
}
