package dev.sebastiano.pushbeat.api.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiGenericResult(
    @SerialName("success") val success: Boolean
) {

    companion object {

        fun success() = ApiGenericResult(true)
    }
}
