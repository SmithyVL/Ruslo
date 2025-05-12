package ru.blimfy.user.db.entity

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.user.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о пользователе.
 *
 * @property username логин.
 * @property email электронная почта.
 * @property verified был ли подтвержден адрес электронной почты.
 * @property globalName отображаемое имя пользователя.
 * @property avatar ссылка на файл аватарки.
 * @property bannerColor цвет баннера пользователя.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @property passwordHash хэш пароля пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Suppress("SpringDataJdbcAssociatedDbElementsInspection")
@Table("users")
data class User(val username: String, val email: String) : BaseEntity() {
    var verified: Boolean = false

    var globalName: String? = null

    var avatar: String? = null

    var bannerColor: String? = null

    @CreatedDate
    lateinit var createdDate: Instant

    @Transient
    lateinit var passwordHash: String
}