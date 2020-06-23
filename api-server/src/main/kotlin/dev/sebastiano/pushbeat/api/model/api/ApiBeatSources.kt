package dev.sebastiano.pushbeat.api.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiBeatSources(
    @SerialName("sources") val sources: List<ApiBeatSource>
)
