package ru.blimfy.user.db.entity

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.user.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о пользователе.
 *
 * @property username логин.
 * @property email электронная почта.
 * @property avatarUrl ссылка на файл аватарки.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Suppress("SpringDataJdbcAssociatedDbElementsInspection")
@Table("users")
data class User(val username: String, val email: String) : BaseEntity() {
    var avatarUrl: String? = null

    @CreatedDate
    lateinit var createdDate: Instant
}