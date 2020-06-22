package dev.sebastiano.pushbeat.api.beats

import dev.sebastiano.pushbeat.api.model.BeatSource

internal object BeatSourcesRegistry {
    // WARNING! This is a TERRIBLE way to do things. Avoid singletons like this!
    // This is a demo project so we'll live with it this one time.

    private val sources = mutableMapOf<String, BeatSource>()

    fun get(sourceId: String): BeatSource? = sources[sourceId]

    fun register(source: BeatSource) {
        sources[source.id] = source
    }

    fun unregister(sourceId: String) {
        sources.remove(sourceId)
    }

    fun sources(): List<BeatSource> = sources.values.toList()
}
