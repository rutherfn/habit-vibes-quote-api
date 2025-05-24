package com.nicholas.rutherford.habit.vibes.quote

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuthentication() {
    val publicAccessToken = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
    val privateAccessToken = "fedcba98-7654-3210-fedc-ba9876543210"

    install(Authentication) {
        bearer("public-access") {
            realm = "Quotes API Public Access"
            authenticate { credential ->
                if (credential.token == publicAccessToken) {
                    UserIdPrincipal("public-read-write-access")
                } else {
                    null
                }
            }
        }

        bearer("private-access") {
            realm = "Quotes API Private Access"
            authenticate { credential ->
                if (credential.token == privateAccessToken) {
                    UserIdPrincipal("private-read-write-access")
                } else {
                    null
                }
            }
        }
    }
}
