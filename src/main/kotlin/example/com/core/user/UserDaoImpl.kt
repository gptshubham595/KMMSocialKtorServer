package example.com.core.user

import example.com.core.DatabaseFactory.dbQuery
import example.com.core.data.dao.user.UserDao
import example.com.core.data.models.SignupParams
import example.com.core.data.models.UserTable
import example.com.core.domain.models.User
import example.com.presentation.security.hashPassword
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserDaoImpl : UserDao {
    override suspend fun createUser(signupParams: SignupParams): User? {
        return dbQuery {
            val insertStatement = UserTable.insert {
                it[email] = signupParams.email
                it[password] = hashPassword(signupParams.password)
                it[name] = signupParams.name
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUser(it)
            }
        }
    }

    override suspend fun findUserByEmail(email: String): User? {
        return dbQuery {
            UserTable.select {
                UserTable.email.eq(email)
            }.mapNotNull {
                rowToUser(it)
            }.singleOrNull()
        }
    }

    override suspend fun findUserById(id: Int): User? {
        return dbQuery {
            UserTable.select {
                UserTable.id.eq(id)
            }.mapNotNull {
                rowToUser(it)
            }.singleOrNull()
        }
    }

    private fun rowToUser(row: ResultRow): User {
        return User(
            id = row[UserTable.id],
            name = row[UserTable.name],
            email = row[UserTable.email],
            password = row[UserTable.password],
            bio = row[UserTable.bio],
            avatar = row[UserTable.avatar],
            created_at = row[UserTable.created_at]
        )
    }
}