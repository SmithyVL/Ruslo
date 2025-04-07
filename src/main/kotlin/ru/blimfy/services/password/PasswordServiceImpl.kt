package ru.blimfy.services.password

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.dto.PasswordDto
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
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
    override suspend fun savePassword(password: PasswordDto) =
        passwordRepo.save(password.toEntity()).toDto()

    override suspend fun findUserPassword(userId: UUID) = passwordRepo.findByUserId(userId)
        ?.toDto()
        ?: throw NotFoundException(ru.blimfy.exception.Errors.PASSWORD_BY_USER_ID_NOT_FOUND.msg.format(userId))
}