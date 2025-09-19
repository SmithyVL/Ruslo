package tech.ruslo.domain.user.db.entity

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.domain.user.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о пользователе.
 *
 * @property username логин.
 * @property email электронная почта.
 * @property password хэш пароля пользователя.
 * @property verified был ли подтвержден адрес электронной почты.
 * @property globalName отображаемое имя пользователя.
 * @property avatar ссылка на файл аватарки.
 * @property bannerColor цвет баннера пользователя.
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table("users")
data class User(var username: String, var email: String, var password: String) : BaseEntity() {
    var verified: Boolean = false
    var globalName: String? = null
    var avatar: String? = null
    var bannerColor: String? = null

    @CreatedDate
    lateinit var createdDate: Instant
}