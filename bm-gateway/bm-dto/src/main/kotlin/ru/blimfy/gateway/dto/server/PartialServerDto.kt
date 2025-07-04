package ru.blimfy.gateway.dto.server

import java.util.UUID

/**
 * DTO с частичной информацией о сервере.
 *
 * @property id идентификатор.
 * @property name название.
 * @property icon ссылка на файл иконки.
 * @property bannerColor цвет баннера.
 * @property description описание сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class PartialServerDto(
    val id: UUID,
    val name: String,
    val icon: String? = null,
    val bannerColor: String? = null,
    val description: String? = null,
)