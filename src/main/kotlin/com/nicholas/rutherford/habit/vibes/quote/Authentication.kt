package com.nicholas.rutherford.habit.vibes.quote

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuthentication() {
    val publicAccessToken = System.getenv("DB_AUTHENTICATION_PUBLIC_ACCESS_TOKEN")
    val privateAccessToken = System.getenv("DB_AUTHENTICATION_PRIVATE_ACCESS_TOKEN")

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
