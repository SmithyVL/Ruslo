package ru.blimfy.services.password

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.exception.Errors.PASSWORD_BY_USER_ID_NOT_FOUND
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.Password
import ru.blimfy.persistence.repository.PasswordRepository

/**
 * Реализация интерфейса для работы с паролем пользователя.
 *
 * @property passwordRepo репозиторий для работы с паролями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class PasswordServiceImpl(private val passwordRepo: PasswordRepository) : PasswordService {
    override suspend fun savePassword(password: Password) = passwordRepo.save(password)

    override suspend fun findUserPassword(userId: UUID) = passwordRepo.findByUserId(userId)
        ?: throw NotFoundException(PASSWORD_BY_USER_ID_NOT_FOUND.msg.format(userId))
}