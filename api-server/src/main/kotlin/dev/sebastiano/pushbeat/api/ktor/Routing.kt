package dev.sebastiano.pushbeat.api.ktor

import dev.sebastiano.pushbeat.api.beats.BeatSourcesRegistry
import dev.sebastiano.pushbeat.api.ktor.handlers.handleDeleteBeat
import dev.sebastiano.pushbeat.api.ktor.handlers.handleGetBeat
import dev.sebastiano.pushbeat.api.ktor.handlers.handleGetBeatSources
import dev.sebastiano.pushbeat.api.ktor.handlers.handleGetRefreshBeat
import dev.sebastiano.pushbeat.api.ktor.handlers.handlePutBeat
import dev.sebastiano.pushbeat.api.ktor.html.renderRegisterBeats
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.html.respondHtml
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.delete
import io.ktor.locations.get
import io.ktor.locations.put
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalCoroutinesApi::class, KtorExperimentalLocationsAPI::class)
internal fun Application.setupRouting(json: Json, registry: BeatSourcesRegistry) {
    routing {
        authenticate(AUTHENTICATOR_NAME) {
            get("/") {
                call.respondHtml { renderRegisterBeats(registry) }
            }

            get<BeatsLocation> {
                call.handleGetBeatSources(json, registry)
            }

            get<BeatLocation> {
                call.handleGetBeat(json, registry, it.id)
            }

            put<BeatLocation> {
                call.handlePutBeat(json, registry, it.id)
            }

            delete<BeatLocation> {
                call.handleDeleteBeat(json, registry, it.id)
            }

            get<BeatLocation.Refresh> {
                call.handleGetRefreshBeat(json, registry, it.beatLocation.id)
            }
        }
    }
}
