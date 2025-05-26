package ru.blimfy.user.usecase.user

import java.util.UUID
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.DuplicateException
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.db.repository.UserRepository
import ru.blimfy.user.usecase.exception.UserErrors.USER_ALREADY_EXISTS
import ru.blimfy.user.usecase.exception.UserErrors.USER_NOT_FOUND

/**
 * Реализация интерфейса для работы с пользователем.
 *
 * @property userRepo репозиторий для работы с пользователями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserServiceImpl(private val userRepo: UserRepository) : UserService {
    override suspend fun createUser(user: User) =
        try {
            userRepo.save(user)
        } catch (ex: DuplicateKeyException) {
            throw DuplicateException(USER_ALREADY_EXISTS.msg.format(user.username), ex)
        }

    override suspend fun modifyUser(
        id: UUID,
        newGlobalName: String?,
        newAvatar: String?,
        newBannerColor: String?,
    ) =
        findUser(id)
            .apply {
                globalName = newGlobalName
                avatar = newAvatar
                bannerColor = newBannerColor
            }
            .let { userRepo.save(it) }

    override suspend fun setUsername(id: UUID, username: String) =
        findUser(id)
            .apply { this.username = username }
            .let {
                userRepo.save(it)
            }

    override suspend fun setPassword(id: UUID, password: String) =
        findUser(id).apply { this.password = password }.let { userRepo.save(it) }

    override suspend fun setEmail(id: UUID, email: String) =
        findUser(id).apply { this.email = email }.let { userRepo.save(it) }

    override suspend fun setVerified(id: UUID) =
        findUser(id).apply { verified = true }.let { userRepo.save(it) }

    override suspend fun findUser(id: UUID) =
        userRepo.findById(id) ?: throw NotFoundException(USER_NOT_FOUND.msg.format(id))

    override suspend fun findUser(username: String) =
        userRepo.findByUsername(username) ?: throw NotFoundException(USER_NOT_FOUND.msg.format(username))

    override suspend fun deleteUser(id: UUID) =
        findUser(id).apply { userRepo.deleteById(id) }
}