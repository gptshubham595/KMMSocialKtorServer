package example.com.di

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        // declare used modules
        modules(appModule)
    }
}