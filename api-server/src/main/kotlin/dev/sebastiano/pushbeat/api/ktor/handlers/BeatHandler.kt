package dev.sebastiano.pushbeat.api.ktor.handlers

import dev.sebastiano.pushbeat.api.beats.BeatSourcesRegistry
import dev.sebastiano.pushbeat.api.ktor.respondError
import dev.sebastiano.pushbeat.api.ktor.withCharsetUtf8
import dev.sebastiano.pushbeat.api.model.BeatSource
import dev.sebastiano.pushbeat.api.model.api.ApiBeatSource
import dev.sebastiano.pushbeat.api.model.api.ApiGenericResult
import dev.sebastiano.pushbeat.api.model.api.toApiBeatSource
import io.ktor.application.ApplicationCall
import io.ktor.features.NotFoundException
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respondText
import kotlin.random.Random
import kotlin.random.nextInt
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.serialization.json.Json

internal suspend fun ApplicationCall.handleGetBeat(json: Json, registry: BeatSourcesRegistry, id: String) {
    val apiSource = registry[id]?.toApiBeatSource()
        ?: throw NotFoundException("No beat provider available with ID '$id'")

    val jsonText = json.stringify(ApiBeatSource.serializer(), apiSource)
    respondText(jsonText, ContentType.Application.Json.withCharsetUtf8(), HttpStatusCode.OK)
}

@OptIn(ExperimentalCoroutinesApi::class)
internal suspend fun ApplicationCall.handleGetRefreshBeat(json: Json, registry: BeatSourcesRegistry, id: String) {
    val source = registry[id] ?: throw NotFoundException("No beat provider available with ID '$id'")

    source.beats.send(Random.Default.nextInt(60..120))

    val jsonText = json.stringify(ApiGenericResult.serializer(), ApiGenericResult.success())
    respondText(jsonText, ContentType.Application.Json.withCharsetUtf8(), HttpStatusCode.OK)
}

@OptIn(ExperimentalCoroutinesApi::class)
internal suspend fun ApplicationCall.handlePutBeat(json: Json, registry: BeatSourcesRegistry, id: String) {
    if (registry[id] != null) {
        respondError(json, "A beat provider with ID '$id' is already registered", HttpStatusCode.BadRequest)
        return
    }

    val apiSource = receive<ApiBeatSource>()
    registry.register(BeatSource(apiSource.id, apiSource.name, ConflatedBroadcastChannel(apiSource.lastValue)))

    val jsonText = json.stringify(ApiGenericResult.serializer(), ApiGenericResult.success())
    respondText(jsonText, ContentType.Application.Json.withCharsetUtf8(), HttpStatusCode.OK)
}

internal suspend fun ApplicationCall.handleDeleteBeat(json: Json, registry: BeatSourcesRegistry, id: String) {
    registry.unregister(id) ?: throw NotFoundException("No beat provider available with ID '$id'")

    val jsonText = json.stringify(ApiGenericResult.serializer(), ApiGenericResult.success())
    respondText(jsonText, ContentType.Application.Json.withCharsetUtf8(), HttpStatusCode.OK)
}
