package tech.ruslo.gateway.security.access

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tech.ruslo.gateway.security.AccessErrors.INCORRECT_PASSWORD
import tech.ruslo.starter.reactive.security.service.TokenService

/**
 * Реализация интерфейса для работы с разрешениями, токенами и шифрованием/дешифрованием паролей.
 *
 * @property tokenService сервис для работы с токенами.
 * @property encoder компонент для получения хэша пароля.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class AccessServiceImpl(
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder,
) : AccessService {
    override fun checkPassword(checkPassword: String, password: String) {
        if (!encoder.matches(checkPassword, password)) {
            throw AccessDeniedException(INCORRECT_PASSWORD.msg.format(checkPassword))
        }
    }

    override fun encodePassword(password: String): String =
        encoder.encode(password)!!

    override fun generateToken(username: String, userId: Long) =
        tokenService.generateToken(username, userId)
}