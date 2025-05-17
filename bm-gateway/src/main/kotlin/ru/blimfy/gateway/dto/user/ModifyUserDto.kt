package ru.blimfy.gateway.dto.user

/**
 * DTO с новой информацией о пользователе.
 *
 * @property globalName был ли подтвержден адрес электронной почты.
 * @property avatar ссылка на файл аватарки.
 * @property bannerColor цвет баннера пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyUserDto(val globalName: String? = null, val avatar: String? = null, val bannerColor: String? = null)