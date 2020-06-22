package dev.sebastiano.pushbeat.api.model

import kotlinx.coroutines.flow.Flow

internal data class BeatSource(
    val id: String,
    val name: String,
    val beats: Flow<BeatsValue>
)

internal typealias BeatsValue = Int
