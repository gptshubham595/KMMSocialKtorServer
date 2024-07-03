package example.com.di

import example.com.core.domain.models.ErrorMessage
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val serializerModule = SerializersModule {
    polymorphic(Any::class) {
        subclass(ErrorMessage::class)
    }
}