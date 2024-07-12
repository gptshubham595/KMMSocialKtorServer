package example.com.plugins

import example.com.presentation.route.authRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
//        get("/") {
//            call.respondText("Hello World!")
//        }
//        // Static plugin. Try to access `/static/index.html`
//        static("/static") {
//            resources("static")
//        }
//        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        authRouting()
    }
}
