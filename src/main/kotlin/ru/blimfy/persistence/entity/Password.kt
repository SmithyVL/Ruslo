package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.PasswordDto
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Сущность, которая хранит в себе информацию о пароле пользователя.
 *
 * @property userId идентификатор пользователя. Имя пользователя то же уникальное значение, но если пользователь вдруг
 * захочет поменять его, то придётся обновлять и пароль. Поэтому здесь у нас связь именно по идентификатору.
 * @property hash хэш пароля. Внутри него уже содержится уникальная "соль".
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Password(val userId: UUID, val hash: String) : WithBaseData {
    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}

/**
 * Возвращает DTO представление сущности пароля пользователя.
 */
fun Password.toDto() = PasswordDto(userId, hash, id, createdDate, updatedDate)

/**
 * Возвращает сущность пароля пользователя из DTO.
 */
fun PasswordDto.toEntity() = Password(userId, hash).apply {
    this@toEntity.id?.let { id = it }
    this@toEntity.createdDate?.let { createdDate = it }
    this@toEntity.updatedDate?.let { updatedDate = it }
}