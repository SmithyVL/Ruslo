package ru.blimfy.common.dto.server

/**
 * DTO с информацией о новом сервере.
 *
 * @property name название.
 * @property avatarUrl ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewServerDto(val name: String, val avatarUrl: String? = null)