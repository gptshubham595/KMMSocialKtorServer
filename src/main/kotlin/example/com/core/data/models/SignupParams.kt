package example.com.core.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SignupParams(
    val email: String,
    val password: String,
    val name: String
)