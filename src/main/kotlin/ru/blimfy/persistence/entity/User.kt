package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.UserDto
import ru.blimfy.controllers.dto.SignUpDto
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Сущность, которая хранит в себе информацию о пользователе. Название таблицы пришлось сделать "users", потому что
 * просто "user" не нравится "Postgres".
 *
 * @property username логин.
 * @property email электронная почта.
 * @property avatarUrl ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table("users")
data class User(val username: String, val email: String) : WithBaseData {
    var avatarUrl: String? = null

    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}

/**
 * Возвращает DTO представление сущности пользователя.
 */
fun User.toDto() = UserDto(id, username, email, createdDate, updatedDate, avatarUrl)

/**
 * Возвращает сущность пользователя из DTO регистрации нового пользователя.
 */
fun SignUpDto.toUserEntity() = User(username, email)