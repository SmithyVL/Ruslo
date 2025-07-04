package ru.blimfy.websocket.service

import io.jsonwebtoken.Jwts.parser
import io.jsonwebtoken.security.Keys.hmacShaKeyFor
import java.util.UUID
import java.util.UUID.fromString
import org.springframework.stereotype.Service
import ru.blimfy.websocket.config.properties.JwtProperties

/**
 * Сервис для работы с токенами авторизации (валидация, извлечение данных).
 *
 * @property jwtProperties конфигурация парсинга JWT токенов.
 * @property signingKey ключ подписи токена.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class WsTokenService(private val jwtProperties: JwtProperties) {
    private val signingKey get() = hmacShaKeyFor(jwtProperties.key.toByteArray())

    /**
     * Возвращает идентификатор пользователя из [token] авторизации.
     */
    fun extractUserId(token: String): UUID =
        token
            .let { extractAllClaims(it)[CLAIM_USER_ID, String::class.java] }
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