package ru.blimfy.services.user

import java.util.UUID
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import ru.blimfy.exception.DuplicateException
import ru.blimfy.exception.Errors.USER_ALREADY_EXISTS
import ru.blimfy.exception.Errors.USER_BY_ID_NOT_FOUND
import ru.blimfy.exception.Errors.USER_BY_USERNAME_NOT_FOUND
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.User
import ru.blimfy.persistence.repository.UserRepository

/**
 * Реализация интерфейса для работы с пользователем.
 *
 * @property userRepo репозиторий для работы с пользователями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserServiceImpl(private val userRepo: UserRepository) : UserService {
    override suspend fun saveUser(user: User) =
        try {
            userRepo.save(user)
        } catch (ex: DuplicateKeyException) {
            throw DuplicateException(USER_ALREADY_EXISTS.msg.format(user.username), ex)
        }

    override suspend fun findUser(id: UUID) = userRepo.findById(id)
        ?: throw NotFoundException(USER_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findUser(username: String) = userRepo.findByUsername(username)
        ?: throw NotFoundException(USER_BY_USERNAME_NOT_FOUND.msg.format(username))
}