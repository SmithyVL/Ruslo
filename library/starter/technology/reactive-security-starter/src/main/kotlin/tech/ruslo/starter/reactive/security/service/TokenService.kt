package tech.ruslo.starter.reactive.security.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts.builder
import io.jsonwebtoken.Jwts.parser
import io.jsonwebtoken.security.Keys.hmacShaKeyFor
import java.util.Date
import javax.crypto.SecretKey
import org.springframework.stereotype.Service
import tech.ruslo.starter.reactive.security.properties.JwtProperties

/**
 * Реализация интерфейса для работы с токенами авторизации.
 *
 * @property jwtProperties конфигурация генерации JWT токенов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class TokenService(private val jwtProperties: JwtProperties) {
    val signingKey: SecretKey
        get() = hmacShaKeyFor(jwtProperties.key.toByteArray())

    /**
     * Возвращает токен авторизации для пользователя с [username] и идентификатором [userId].
     */
    fun generateToken(username: String, userId: Long): String =
        builder()
            .claims()
            .issuer(jwtProperties.issuer)
            .subject(username)
            .issuedAt(Date())
            .add(mutableMapOf(CLAIM_USER_ID to userId))
            .and()
            .signWith(signingKey)
            .compact()

    /**
     * Проверяет валидность [token] для имени пользователя - [username].
     */
    fun isValid(token: String, username: String) = username == extractUsername(token)

    /**
     * Возвращает идентификатор пользователя из [token] авторизации.
     */
    fun extractUserId(token: String): Long = token
        .let { extractAllClaims(it)[CLAIM_USER_ID, Any::class.java] }
        .toString()
        .toLong()

    /**
     * Возвращает имя пользователя из [token].
     */
    fun extractUsername(token: String): String = extractAllClaims(token).subject

    /**
     * Возвращает содержимое из [token] в удобном виде.
     */
    fun extractAllClaims(token: String): Claims = parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .payload

    private companion object {
        /**
         * Название одной из "начинок" токена. Об идентификаторе пользователя.
         */
        private const val CLAIM_USER_ID = "userId"
    }
}