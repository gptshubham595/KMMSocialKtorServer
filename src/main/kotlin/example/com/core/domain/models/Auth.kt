package example.com.core.domain.models

import example.com.utils.Either
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val data: /*Either<ErrorMessage, */AuthResponseData/*>*/? = null,
    val errorMessage: String? = null
)

@Serializable
data class ErrorMessage(
    val message: String
)

@Serializable
data class AuthResponseData(
    val id: Int,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val token: String,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)