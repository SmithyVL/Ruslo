package ru.blimfy.services.user

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.exception.Errors.USER_BY_ID_NOT_FOUND
import ru.blimfy.exception.Errors.USER_BY_USERNAME_NOT_FOUND
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.User
import ru.blimfy.persistence.repository.UserRepository

/**
 * Реализация интерфейса для работы с пользователем.
 *
 * @property repository репозиторий для работы с серверами в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {
    override suspend fun saveUser(user: User) = repository.save(user)

    override suspend fun findUser(id: UUID) = repository.findById(id)
        ?: throw NotFoundException(USER_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findUser(username: String) = repository.findByUsername(username)
        ?: throw NotFoundException(USER_BY_USERNAME_NOT_FOUND.msg.format(username))
}