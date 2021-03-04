import dev.sebastiano.pushbeat.gradle.*
import io.gitlab.arturbosch.detekt.*
import org.gradle.kotlin.dsl.detekt
import org.jetbrains.kotlin.gradle.tasks.*

buildscript {
    repositories {
        jcenter()
        mavenLocal()
    }

    extra["kotlin_version"] = "1.3.72"
    extra["detekt_version"] = "1.9.1"
    extra["ktlint_version"] = "0.36.0"
    extra["ktor_version"] = "1.3.2"

    configurations.classpath.get()
            .resolutionStrategy.force("com.github.pinterest:ktlint:${extra["ktlint_version"]}")
}

plugins {
    kotlin("jvm") version "1.4.31" apply false
    id("io.gitlab.arturbosch.detekt") version "1.9.1"
    id("org.jmailen.kotlinter") version "2.3.2"
    id("com.github.ben-manes.versions") version "0.38.0"
}

group = "dev.sebastiano"
version = "1.0"

detekt {
    toolVersion = extra["detekt_version"].toString()
    autoCorrect = !isCi()
    input = files("src/main/java", "src/main/kotlin", "buildSrc/src/main/kotlin")
    config = files(buildConfigFile("detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

allprojects {
    buildscript {
        repositories {
            google()
            mavenCentral()
            jcenter()
        }
    }

    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    apply(plugin = "org.jmailen.kotlinter")

    kotlinter {
        indentSize = 4
        reporters = arrayOf("html", "checkstyle", "plain")
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        toolVersion = rootProject.extra["detekt_version"].toString()
        autoCorrect = !isCi()
        config = files(buildConfigFile("detekt/detekt.yml"))
        buildUponDefaultConfig = true
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-progressive")
            }
        }

        withType<Detekt> {
            jvmTarget = "1.8"
        }
    }
}
