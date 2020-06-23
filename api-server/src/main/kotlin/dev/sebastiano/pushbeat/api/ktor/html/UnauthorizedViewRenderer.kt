package dev.sebastiano.pushbeat.api.ktor.html

import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.p

internal fun HTML.renderUnauthorizedView() {
    body {
        h1 { +"PushBeat" }
        p {
            +"You're not authenticated, sorry."
        }
    }
}
