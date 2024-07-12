package example.com.presentation.route

import example.com.core.data.models.LoginParams
import example.com.core.data.models.SignupParams
import example.com.core.domain.models.AuthResponse
import example.com.core.domain.repositories.AuthRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.authRouting() {
    val authRepository by inject<AuthRepository>()
    route(path = "/") {
        get {
            call.respondText("Hello World!")
        }
    }
    route(path = "/auth/signup") {
        post {
            // here we will receive the signup params and it serialize it to the SignupParams class
            val params = call.receiveNullable<SignupParams>()

            // if params is null that means the request body is empty or is unable to serialize to the SignupParams class
            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(errorMessage = "Invalid request body")
                )
                return@post
            }

            val result = authRepository.signUp(params)
            call.respond(
                status = result.code,
                message = result.data
            )

        }
    }

    route(path = "/auth/login") {
        post {
            // here we will receive the signup params and it serialize it to the SignupParams class
            val params = call.receiveNullable<LoginParams>()

            // if params is null that means the request body is empty or is unable to serialize to the SignupParams class
            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(errorMessage = "Invalid request body")
                )
                return@post
            }

            val result = authRepository.login(params)
            call.respond(
                status = result.code,
                message = result.data
            )

        }
    }
}