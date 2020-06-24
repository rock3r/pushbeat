package dev.sebastiano.pushbeat.api.ktor.html

import dev.sebastiano.pushbeat.api.beats.BeatSourcesRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.html.HTML
import kotlinx.html.LinkType
import kotlinx.html.b
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.code
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.i
import kotlinx.html.li
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.span
import kotlinx.html.styleLink
import kotlinx.html.title
import kotlinx.html.ul

@OptIn(ExperimentalCoroutinesApi::class)
internal fun HTML.renderRegisterBeats(registry: BeatSourcesRegistry) {
    head {
        title { +"PushBeat" }
        styleLink("/static/css/pushbeat.css")
        script(src = "/static/js/pushbeat.js", type = LinkType.textJavaScript) {}
    }

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
                        b {
                            +"${source.name} "
                            span(classes = "secondary") {
                                +"(id: "
                                code { +source.id }
                                +")"
                            }
                        }
                        br
                        +"Last BPM: ${source.beats.valueOrNull ?: "N/A"}"
                        span(classes = "refresh") {
                            onClick = "javascript:refreshBeatValue('${source.id}'); location.reload();"
                            +"refresh"
                        }
                    }
                }
            }
        }
    }
}
