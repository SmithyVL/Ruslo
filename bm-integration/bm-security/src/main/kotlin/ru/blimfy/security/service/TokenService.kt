package ru.blimfy.security.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.parser
import io.jsonwebtoken.security.Keys.hmacShaKeyFor
import java.util.Date
import java.util.UUID
import java.util.UUID.fromString
import org.springframework.stereotype.Service
import ru.blimfy.security.config.properties.SecurityProperties

/**
 * Сервис для работы с токенами авторизации (создание, валидация, извлечение данных).
 *
 * @property securityProperties конфигурация генерации JWT токенов.
 * @property signingKey ключ для подписания токена.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class TokenService(private val securityProperties: SecurityProperties) {
    private val signingKey get() = hmacShaKeyFor(securityProperties.jwt.key.toByteArray())

    /**
     * Возвращает токен авторизации для пользователя с [username] и идентификатором [userId].
     */
    fun generateToken(username: String, userId: UUID): String =
        Jwts.builder()
            .claims()
            .issuer(securityProperties.jwt.issuer)
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
     * Возвращает имя пользователя из [token].
     */
    fun extractUsername(token: String): String = extractAllClaims(token).subject

    /**
     * Возвращает идентификатор пользователя из [token] авторизации.
     */
    fun extractUserId(token: String): UUID =
        token
            .let { extractAllClaims(it).get(CLAIM_USER_ID, String::class.java) }
            .let { fromString(it) }

    /**
     * Возвращает содержимое из [token] в удобном виде.
     */
    private fun extractAllClaims(token: String) =
        parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload

    private companion object {
        /**
         * Название одной из "начинок" токена. Об идентификаторе пользователя.
         */
        const val CLAIM_USER_ID = "userId"
    }
}