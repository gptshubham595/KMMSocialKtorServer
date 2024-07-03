package example.com.presentation.security

import io.ktor.util.hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val ALGORITHM = "HmacSHA1"
private val HASH_SECRET = "hashKey"
private val HMAC_KEY = SecretKeySpec(HASH_SECRET.toByteArray(), ALGORITHM)

fun hashPassword(password: String): String {
    val hmac = Mac.getInstance(ALGORITHM)
    hmac.init(HMAC_KEY)
    return hex(hmac.doFinal(password.toByteArray()))
}