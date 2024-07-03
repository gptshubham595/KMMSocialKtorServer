package example.com.core.user

import example.com.core.data.dao.user.UserDao
import example.com.core.data.models.LoginParams
import example.com.core.data.models.SignupParams
import example.com.core.domain.models.AuthResponse
import example.com.core.domain.models.AuthResponseData
import example.com.core.domain.repositories.AuthRepository
import example.com.utils.Response
import io.ktor.http.HttpStatusCode

class AuthRepositoryImpl(
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun signUp(signupParams: SignupParams): Response<AuthResponse> {
        if (userAlreadyExists(signupParams.email)) {
            // user already exists
            // send error response
            return Response.Error(
                HttpStatusCode.Conflict,
                data = AuthResponse(errorMessage = "User already exists")
            )
        } else {
            // create user
            val insertedUser = userDao.createUser(signupParams)
                ?: run {
                    // user creation failed
                    // send error response
                    return Response.Error(
                        HttpStatusCode.InternalServerError,
                        data = AuthResponse(errorMessage = "User creation failed")
                    )
                }
            return Response.Success(
                AuthResponse(
                    data = AuthResponseData(
                        id = insertedUser.id,
                        name = insertedUser.name,
                        bio = insertedUser.bio,
                        avatar = insertedUser.avatar,
                        token = "token",
                    )
                )
            )
        }
    }

    override suspend fun login(loginParams: LoginParams): Response<AuthResponse> {
        TODO("Not yet implemented")
    }

    private suspend fun userAlreadyExists(email: String): Boolean {
        return userDao.findUserByEmail(email) != null
    }
}