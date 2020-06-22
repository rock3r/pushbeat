package dev.sebastiano.pushbeat.api.ktor

import dev.sebastiano.pushbeat.api.AuthenticationException
import dev.sebastiano.pushbeat.api.AuthorizationException
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.request.uri
import kotlinx.serialization.json.Json
import org.slf4j.Logger

internal fun Application.setupStatusPages(json: Json, logger: Logger) {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { statusCode ->
            val message = generate404ErrorMessage(call.request.uri)
            call.respondError(json, message, statusCode)
        }

        exception<AuthenticationException> {
            call.respondError(json, "Unauthorized", HttpStatusCode.Unauthorized)
        }

        exception<AuthorizationException> {
            call.respondError(json, "Forbidden", HttpStatusCode.Unauthorized)
        }

        exception<Throwable> { cause ->
            logger.info("Unhandled exception: ${cause.message}", cause)
            val errorMessage = "Error while processing the request. ${cause.message}"
            call.respondError(json, errorMessage, HttpStatusCode.InternalServerError)
        }
    }
}
