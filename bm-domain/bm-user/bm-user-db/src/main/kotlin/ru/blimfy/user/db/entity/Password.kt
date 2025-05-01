package ru.blimfy.user.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.user.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о пароле пользователя.
 *
 * @property userId идентификатор пользователя.
 * @property hash хэш пароля. Внутри него уже содержится уникальная "соль".
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Password(val userId: UUID, val hash: String) : BaseEntity()