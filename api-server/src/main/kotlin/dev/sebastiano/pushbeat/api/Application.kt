@file:Suppress("EXPERIMENTAL_API_USAGE")

package dev.sebastiano.pushbeat.api

import dev.sebastiano.pushbeat.api.beats.BeatSourcesRegistry
import dev.sebastiano.pushbeat.api.ktor.setupAuthentication
import dev.sebastiano.pushbeat.api.ktor.setupRouting
import dev.sebastiano.pushbeat.api.ktor.setupStatusPages
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.deflate
import io.ktor.features.gzip
import io.ktor.features.minimumSize
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.request.accept
import io.ktor.request.httpMethod
import io.ktor.request.uri
import io.ktor.server.servlet.ServletApplicationEngine
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

private val logger = LoggerFactory.getLogger("Ktor")

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    setupAuthentication()

    install(Locations) {
    }

    @Suppress("MagicNumber") // Just one-off configuration
    install(Compression) {
        gzip()
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(ContentNegotiation) {
    }

    install(CallLogging) {
        level = Level.INFO
        format { call ->
            when (val status = call.response.status() ?: "Unhandled") {
                HttpStatusCode.Found -> "$status: ${call.request.httpMethod.value} - ${call.request.uri} [accept: '${call.request.accept()}'] -> " +
                    "${call.response.headers[HttpHeaders.Location]}"
                else -> "$status: ${call.request.httpMethod.value} - ${call.request.uri} [accept: '${call.request.accept()}']"
            }
        }
    }

    val json = createJsonInstance()

    setupStatusPages(json, logger)
    setupRouting(BeatSourcesRegistry)
}
