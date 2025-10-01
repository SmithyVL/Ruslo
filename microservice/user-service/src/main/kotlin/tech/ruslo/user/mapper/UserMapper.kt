package tech.ruslo.user.mapper

import tech.ruslo.user.database.entity.User
import tech.ruslo.user.dto.UserDto

/**
 * Возвращает сущность пользователя из DTO представления нового пользователя.
 */
fun UserDto.toEntity() = User(username, passwordHash).apply {
    this@toEntity.id?.let { id = it }
    this@toEntity.createdDate?.let { createdDate = it }
}

/**
 * Возвращает DTO представление с информацией из сущности пользователя.
 */
fun User.toDto() = UserDto(id, username, password, createdDate)