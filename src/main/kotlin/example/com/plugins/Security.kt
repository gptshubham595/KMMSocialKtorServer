package example.com.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import example.com.core.domain.models.AuthResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import java.util.Date

val jwtAudience = "jwt-audience"
val jwtDomain = "https://jwt-provider-domain/"
val jwtRealm = "ktor sample app"
val jwtSecret = "secret"

fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain

    authentication {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
//                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
                if(credential.payload.getClaim("email").asString() != null) JWTPrincipal(credential.payload) else null
            }

            challenge { defaultScheme, realm ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = AuthResponse(errorMessage = "Invalid Token or Token Expired!")
                )
            }
        }
    }
}
fun generateToken(email: String) : String{
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtDomain)
        .withClaim("email", email)
//        .withExpiresAt(Date(System.currentTimeMillis() + 60000))
        .sign(Algorithm.HMAC256(jwtSecret))

}
