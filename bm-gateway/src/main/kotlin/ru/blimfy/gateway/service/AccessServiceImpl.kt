package ru.blimfy.gateway.service

import java.util.UUID
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.IncorrectDataException
import ru.blimfy.gateway.exception.GatewayErrors.INCORRECT_PASSWORD
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.server.ServerService

/**
 * Реализация интерфейса для работы с разрешениями, токенами и шифрованием/дешифрованием паролей.
 *
 * @property serverService сервис для работы с серверами.
 * @property tokenService сервис для работы с токенами.
 * @property encoder компонент для получения хэша пароля.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class AccessServiceImpl(
    private val serverService: ServerService,
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder,
): AccessService {
    override suspend fun isServerOwner(serverId: UUID, userId: UUID) =
        serverService.findServer(serverId).ownerId == userId

    override fun checkPassword(checkPassword: String, password: String) {
        if (!encoder.matches(checkPassword, password)) {
            throw IncorrectDataException(INCORRECT_PASSWORD.msg.format(checkPassword))
        }
    }

    override fun encodePassword(password: String): String = encoder.encode(password)

    override fun generateToken(username: String, userId: UUID) = tokenService.generateToken(username, userId)
}