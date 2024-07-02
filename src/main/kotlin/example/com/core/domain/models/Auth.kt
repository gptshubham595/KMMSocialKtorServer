package example.com.core.domain.models

import example.com.utils.Either

data class AuthResponse(
    val data: Either<ErrorMessage, AuthResponseData>? = null,
)

data class ErrorMessage(
    val message: String
)

data class AuthResponseData(
    val id: Int,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val token: String,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)