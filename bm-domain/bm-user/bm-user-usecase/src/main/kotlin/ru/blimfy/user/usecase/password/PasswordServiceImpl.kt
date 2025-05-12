package ru.blimfy.user.usecase.password

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.user.db.entity.Password
import ru.blimfy.user.db.repository.PasswordRepository
import ru.blimfy.user.usecase.exception.UserErrors.PASSWORD_NOT_FOUND

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
        ?: throw NotFoundException(PASSWORD_NOT_FOUND.msg.format(userId))
}