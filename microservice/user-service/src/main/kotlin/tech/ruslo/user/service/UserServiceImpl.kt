package tech.ruslo.user.service

import org.springframework.stereotype.Service
import tech.ruslo.exceptions.core.NotFoundException
import tech.ruslo.user.database.entity.User
import tech.ruslo.user.database.repository.UserRepository
import tech.ruslo.user.exception.UserErrors.USER_NOT_FOUND

/**
 * Реализация интерфейса для работы с пользователем.
 *
 * @property userRepo репозиторий для работы с пользователями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserServiceImpl(private val userRepo: UserRepository) : UserService {
    override suspend fun saveUser(user: User) = userRepo.save(user)

    override suspend fun findUser(id: Long) = userRepo.findById(id)
        ?: throw NotFoundException(USER_NOT_FOUND.msg.format(id))

    override suspend fun findUser(username: String) = userRepo.findByUsername(username)
        ?: throw NotFoundException(USER_NOT_FOUND.msg.format(username))
}