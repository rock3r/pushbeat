package dev.sebastiano.pushbeat.api.ktor

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic

internal const val AUTHENTICATOR_NAME = "BasicAuth"

internal fun Application.setupAuthentication() {
    install(Authentication) {
        basic(AUTHENTICATOR_NAME) {
            realm = "PushBeat"
            validate {
                if (it.name == "push" && it.password == "beat") UserIdPrincipal(it.name) else null
            }
        }
    }
}
