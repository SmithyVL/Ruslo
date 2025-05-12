package ru.blimfy.gateway.dto.user

import java.time.Instant
import java.util.UUID
import ru.blimfy.user.db.entity.User

/**
 * DTO с новой информацией о пользователе.
 *
 * @property email электронная почта.
 * @property globalName отображаемое имя пользователя.
 * @property verified был ли подтвержден адрес электронной почты.
 * @property avatar ссылка на файл аватарки.
 * @property bannerColor цвет баннера пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyUserDto(
    val email: String,
    val verified: Boolean,
    val globalName: String? = null,
    val avatar: String? = null,
    val bannerColor: String? = null,
)

/**
 * Возвращает сущность пользователя с [id], [verified], [createdDate] и [username] из DTO представления пользователя.
 */
fun ModifyUserDto.toEntity(id: UUID, username: String, verified: Boolean, createdDate: Instant) =
    User(username, email).apply {
        globalName = this@toEntity.globalName
        avatar = this@toEntity.avatar
        bannerColor = this@toEntity.bannerColor

        // Неизменяемые или изменяемые только системой параметры.
        this.id = id
        this.verified = verified
        this.createdDate = createdDate
    }