package example.com.core.user

import example.com.core.data.dao.user.UserDao
import example.com.core.data.models.LoginParams
import example.com.core.data.models.SignupParams
import example.com.core.domain.models.AuthResponse
import example.com.core.domain.models.AuthResponseData
import example.com.core.domain.models.ErrorMessage
import example.com.core.domain.repositories.UserRepository
import example.com.utils.Either
import example.com.utils.Response
import io.ktor.http.HttpStatusCode

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun signUp(signupParams: SignupParams): Response<AuthResponse> {
        if (userAlreadyExists(signupParams.email)) {
            // user already exists
            // send error response
            return Response.Error(
                HttpStatusCode.Conflict,
                data = AuthResponse(Either.Failure(ErrorMessage("User already exists")))
            )
        } else {
            // create user
            val insertedUser = userDao.createUser(signupParams)
            if (insertedUser == null) {
                // user creation failed
                // send error response
                return Response.Error(
                    HttpStatusCode.InternalServerError,
                    data = AuthResponse(Either.Failure(ErrorMessage("User creation failed")))
                )
            }
            return Response.Success(
                AuthResponse(
                    Either.Success(
                        AuthResponseData(
                            id = insertedUser.id,
                            name = insertedUser.name,
                            bio = insertedUser.bio,
                            avatar = insertedUser.avatar,
                            token = "token",
                        )
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