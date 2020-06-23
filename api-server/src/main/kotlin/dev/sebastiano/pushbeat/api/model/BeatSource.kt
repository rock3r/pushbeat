package dev.sebastiano.pushbeat.api.model

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

internal data class BeatSource @OptIn(ExperimentalCoroutinesApi::class) constructor(
    val id: String,
    val name: String,
    val beats: ConflatedBroadcastChannel<BeatsValue>
)

internal typealias BeatsValue = Int
