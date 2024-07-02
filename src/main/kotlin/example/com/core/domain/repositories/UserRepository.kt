package example.com.core.domain.repositories

import example.com.core.data.models.LoginParams
import example.com.core.data.models.SignupParams
import example.com.core.domain.models.AuthResponse
import example.com.utils.Response

interface UserRepository {
    suspend fun signUp(signupParams: SignupParams): Response<AuthResponse>
    suspend fun login(loginParams: LoginParams): Response<AuthResponse>
}