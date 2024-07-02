package example.com.core.data.dao.user

import example.com.core.data.models.SignupParams
import example.com.core.domain.models.User

interface UserDao {
    suspend fun createUser(signupParams: SignupParams): User?
    suspend fun findUserByEmail(email: String): User?
    suspend fun findUserById(id: Int): User?
}