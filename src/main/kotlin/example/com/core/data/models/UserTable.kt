package example.com.core.data.models


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime


object UserTable : Table(name = "users") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 250)
    val email = varchar("email", 250)
    val password = varchar("password", 100)
    val bio = text("bio").default(
        "Hey there! I am using KMM Social App."
    )
    val avatar = text("avatar").nullable()
    val created_at = datetime("created_at").default(
        LocalDateTime.now()
    )

    override val primaryKey = PrimaryKey(id)
}