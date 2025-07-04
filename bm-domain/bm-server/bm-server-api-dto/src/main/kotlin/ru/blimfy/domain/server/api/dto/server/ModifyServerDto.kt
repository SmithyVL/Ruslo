package ru.blimfy.domain.server.api.dto.server

/**
 * DTO с обновлённой информацией о сервере.
 *
 * @property name название.
 * @property icon ссылка на файл аватарки.
 * @property bannerColor цвет баннера сервера.
 * @property description описание сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyServerDto(
    val name: String,
    val icon: String? = null,
    val bannerColor: String? = null,
    val description: String? = null,
)