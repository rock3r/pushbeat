package dev.sebastiano.pushbeat.api.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    @SerialName("error") val error: ApiError
) {

    @Serializable
    data class ApiError(
        @SerialName("message") val message: String,
        @SerialName("stack_trace") val stackTrace: String? = null
    )

    companion object {

        fun with(message: String, stackTrace: String? = null) = ApiErrorResponse(ApiError(message, stackTrace))
    }
}
