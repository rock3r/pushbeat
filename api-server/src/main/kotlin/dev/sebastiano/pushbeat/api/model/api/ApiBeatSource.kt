package dev.sebastiano.pushbeat.api.model.api

import dev.sebastiano.pushbeat.api.model.BeatSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiBeatSource(
    @SerialName("id")val id: String,
    @SerialName("name") val name: String,
    @SerialName("last_value") val lastValue: Int
)

@OptIn(ExperimentalCoroutinesApi::class)
internal fun BeatSource.toApiBeatSource() = ApiBeatSource(id, name, beats.value)
