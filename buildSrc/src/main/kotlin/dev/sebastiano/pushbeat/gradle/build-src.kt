@file:JvmName("BuildSrc")
package dev.sebastiano.pushbeat.gradle

import org.gradle.api.Project
import java.io.File

fun isCi() = System.getenv("CI") != null ||
    System.getenv("CONTINUOUS_INTEGRATION") != null ||
    System.getenv("TEAMCITY_VERSION") != null

fun Project.buildConfigFile(path: String) = File(rootDir, "build-config/$path")
