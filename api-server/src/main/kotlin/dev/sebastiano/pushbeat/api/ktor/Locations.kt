@file:Suppress("EXPERIMENTAL_API_USAGE")

package dev.sebastiano.pushbeat.api.ktor

import io.ktor.locations.Location

@Location("/beats")
class BeatsLocation

@Location("/beat/{id}")
class BeatLocation(val id: String) {

    @Location("/refresh")
    class Refresh(val beatLocation: BeatLocation)
}
