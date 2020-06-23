package dev.sebastiano.pushbeat.api.ktor.handlers

import dev.sebastiano.pushbeat.api.beats.BeatSourcesRegistry
import dev.sebastiano.pushbeat.api.ktor.withCharsetUtf8
import dev.sebastiano.pushbeat.api.model.api.ApiBeatSources
import dev.sebastiano.pushbeat.api.model.api.toApiBeatSource
import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import kotlinx.serialization.json.Json

internal suspend fun ApplicationCall.handleGetBeatSources(json: Json, registry: BeatSourcesRegistry) {
    val apiSources = ApiBeatSources(registry.sources().map { it.toApiBeatSource() })
    val jsonText = json.stringify(ApiBeatSources.serializer(), apiSources)
    respondText(jsonText, ContentType.Application.Json.withCharsetUtf8(), HttpStatusCode.OK)
}
