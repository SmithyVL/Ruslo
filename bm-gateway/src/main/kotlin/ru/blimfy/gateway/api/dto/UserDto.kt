package ru.blimfy.gateway.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.user.db.entity.User

/**
 * DTO с информацией о пользователе.
 *
 * @property id идентификатор.
 * @property username логин.
 * @property email электронная почта.
 * @property globalName отображаемое имя пользователя.
 * @property verified был ли подтвержден адрес электронной почты.
 * @property avatar ссылка на файл аватарки.
 * @property bannerColor цвет баннера пользователя.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @property token токен авторизации.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(NON_NULL)
data class UserDto(
    val id: UUID,
    val username: String,
    val email: String,
    val verified: Boolean = false,
    val globalName: String? = null,
    val avatar: String? = null,
    val bannerColor: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
) {
    var token: String? = null
}

/**
 * Возвращает DTO представление с информацией из сущности пользователя.
 */
fun User.toDto() = UserDto(id, username, email, verified, globalName, avatar, bannerColor, createdDate)