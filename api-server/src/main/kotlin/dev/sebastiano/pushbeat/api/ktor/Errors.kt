package dev.sebastiano.pushbeat.api.ktor

import dev.sebastiano.pushbeat.api.model.api.ApiErrorResponse
import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import kotlinx.serialization.json.Json

internal suspend fun ApplicationCall.respondError(
    json: Json,
    errorMessage: String,
    statusCode: HttpStatusCode
) {
    val errorContent = generateError(json, errorMessage)
    respondText(errorContent, errorContentType, statusCode)
}

private val errorContentType = ContentType.Application.Json.withCharsetUtf8()

private fun generateError(json: Json, errorMessage: String): String = json.stringify(
    ApiErrorResponse.serializer(),
    ApiErrorResponse.with(errorMessage)
)
