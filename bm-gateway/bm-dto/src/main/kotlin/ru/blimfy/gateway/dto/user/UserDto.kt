package ru.blimfy.gateway.dto.user

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.common.json.INSTANT_FORMAT
import ru.blimfy.common.json.INSTANT_TIMEZONE

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
data class UserDto(
    val id: UUID,
    val username: String,
    val email: String,
    val verified: Boolean = false,
    val globalName: String? = null,
    val avatar: String? = null,
    val bannerColor: String? = null,
    @param:JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
) {
    var token: String? = null
}