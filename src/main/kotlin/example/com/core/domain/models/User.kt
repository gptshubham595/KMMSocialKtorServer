package example.com.core.domain.models
import java.time.LocalDateTime



data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val bio: String,
    val avatar: String?,
    val created_at: LocalDateTime
)