package example.com.presentation.route

import example.com.core.data.models.SignupParams
import example.com.core.domain.repositories.UserRepository
import example.com.utils.Either
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.authRouting() {
    val userRepository by inject<UserRepository>()

    route(path = "/signup") {
        post {
            // here we will receive the signup params and it serialize it to the SignupParams class
            val params = call.receiveNullable<SignupParams>()

            // if params is null that means the request body is empty or is unable to serialize to the SignupParams class
            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Either.Failure("Invalid request body")
                )
                return@post
            }

            val result = userRepository.signUp(params)
            call.respond(
                status = result.code,
                message = result.data
            )

        }
    }
}