package dev.sebastiano.pushbeat.api.ktor.html

import dev.sebastiano.pushbeat.api.beats.BeatSourcesRegistry
import kotlinx.html.HTML
import kotlinx.html.b
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.i
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.ul

internal fun HTML.renderRegisterBeats(registry: BeatSourcesRegistry) {
    body {
        h1 { +"PushBeat" }
        h2 { +"Registered beats" }
        ul {
            val sources = registry.sources()
            if (sources.isEmpty()) {
                li { i { +"None" } }
                return@ul
            }

            for (source in sources) {
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
