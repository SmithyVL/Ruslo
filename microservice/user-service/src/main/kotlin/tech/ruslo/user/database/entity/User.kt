package tech.ruslo.user.database.entity

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.common.database.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о пользователе.
 *
 * @property username логин.
 * @property password хэш пароля пользователя.
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table("users")
data class User(var username: String, var password: String) : BaseEntity() {
    @CreatedDate
    lateinit var createdDate: Instant
}