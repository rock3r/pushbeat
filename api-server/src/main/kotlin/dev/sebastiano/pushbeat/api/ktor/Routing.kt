package dev.sebastiano.pushbeat.api.ktor

import dev.sebastiano.pushbeat.api.beats.BeatSourcesRegistry
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.html.respondHtml
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.get
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.b
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.ul

@KtorExperimentalLocationsAPI
internal fun Application.setupRouting(registry: BeatSourcesRegistry) {
    routing {
        authenticate(AUTHENTICATOR_NAME) {
            get("/") {
                call.respondHtml {
                    body {
                        h1 { +"PushBeat" }
                        h2 { +"Registered beats" }
                        ul {
                            for (source in registry.sources()) {
                                li {
                                    p {
                                        b { +"${source.name} (id: ${source.id}" }
                                        br
                                        +"Last BPM: ${source.beats}"
                                    }
                                }
                            }
                        }
                    }
                }
            }

//            get<BeatsLocation> {
//                call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
//            }
//
//            get<BeatLocation> {
//                call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
//            }
//
//            get<BeatLocation.Refresh> {
//                call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
//            }

        }
    }
}
