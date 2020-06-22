package dev.sebastiano.pushbeat.api

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

fun createJsonInstance() = Json(
    JsonConfiguration.Stable.copy(ignoreUnknownKeys = true, encodeDefaults = false)
)
