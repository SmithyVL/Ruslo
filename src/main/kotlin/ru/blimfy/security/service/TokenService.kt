package ru.blimfy.security.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.parser
import io.jsonwebtoken.security.Keys.hmacShaKeyFor
import java.security.Principal
import java.util.Date
import java.util.UUID
import java.util.UUID.fromString
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import ru.blimfy.security.config.JwtProperties

/**
 * Сервис для работы с токенами авторизации (создание, валидация, извлечение данных).
 *
 * @property jwtProperties конфигурация генерации JWT токенов.
 * @property signingKey ключ для подписания токена.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class TokenService(private val jwtProperties: JwtProperties) {
    private val signingKey get() = hmacShaKeyFor(jwtProperties.key.toByteArray())

    /**
     * Возвращает токен авторизации для пользователя с [username] и идентификатором [userId].
     */
    fun generateToken(username: String, userId: UUID): String =
        Jwts.builder()
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
     * Возвращает имя пользователя из [token].
     */
    fun extractUsername(token: String): String = extractAllClaims(token).subject

    /**
     * Возвращает идентификатор пользователя из информации об авторизации [principal], в которой хранится токен
     * авторизации.
     */
    fun extractUserId(principal: Principal): UUID =
        (principal as UsernamePasswordAuthenticationToken)
            .credentials
            .toString()
            .let { extractAllClaims(it).get(CLAIM_USER_ID, String::class.java) }
            .let { fromString(it) }

    /**
     * Возвращает идентификатор пользователя из HTTP [headers], в которой хранится токен авторизации.
     */
    fun extractUserId(headers: HttpHeaders?) =
        extractToken(headers)
            ?.let { token ->
                token
                    .let { extractAllClaims(it).get(CLAIM_USER_ID, String::class.java) }
                    .let { fromString(it) }
            }!!

    /**
     * Возвращает содержимое из [token] в удобном виде.
     */
    private fun extractAllClaims(token: String) =
        parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload

    companion object {
        /**
         * Название одной из "начинок" токена. Об идентификаторе пользователя.
         */
        private const val CLAIM_USER_ID = "userId"

        /**
         * Возвращает JWT токен из [headers] или null, если его нет.
         */
        fun extractToken(headers: HttpHeaders?) = headers
            ?.getFirst("Authorization")
            ?.substringAfter("Bearer ")
    }
}