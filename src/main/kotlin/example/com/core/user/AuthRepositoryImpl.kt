package example.com.core.user

import example.com.core.data.dao.user.UserDao
import example.com.core.data.models.LoginParams
import example.com.core.data.models.SignupParams
import example.com.core.domain.models.AuthResponse
import example.com.core.domain.models.AuthResponseData
import example.com.core.domain.models.User
import example.com.core.domain.repositories.AuthRepository
import example.com.plugins.generateToken
import example.com.presentation.security.hashPassword
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
                        token = generateToken(insertedUser.email),
                        followersCount = 0,
                        followingCount = 0,
                    )
                )
            )
        }
    }

    override suspend fun login(loginParams: LoginParams): Response<AuthResponse> {
        val user = getUserByEmail(loginParams.email)
            ?: run {
                // user not found
                // send error response
                return Response.Error(
                    HttpStatusCode.NotFound,
                    data = AuthResponse(errorMessage = "User not found")
                )
            }
        val hashPassword = hashPassword(loginParams.password)
        return if (user.password == hashPassword) {
            Response.Success(
                AuthResponse(
                    data = AuthResponseData(
                        id = user.id,
                        name = user.name,
                        bio = user.bio,
                        avatar = user.avatar,
                        token = generateToken(user.email),
                    )
                )
            )
        } else {
            // password mismatch
            // send error response
            Response.Error(
                HttpStatusCode.Unauthorized,
                data = AuthResponse(errorMessage = "Invalid Credentials!")
            )
        }
    }

    private suspend fun userAlreadyExists(email: String): Boolean {
        return getUserByEmail(email) != null
    }

    private suspend fun getUserByEmail(email: String): User? {
        return userDao.findUserByEmail(email)
    }
}