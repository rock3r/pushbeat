package dev.sebastiano.pushbeat.api.ktor

import io.ktor.http.ContentType
import io.ktor.http.withCharset

fun ContentType.withCharsetUtf8(): ContentType = withCharset(Charsets.UTF_8)
