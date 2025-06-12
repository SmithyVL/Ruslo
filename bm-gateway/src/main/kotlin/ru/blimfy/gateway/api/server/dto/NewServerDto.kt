package ru.blimfy.gateway.api.server.dto

/**
 * DTO с информацией о новом сервере.
 *
 * @property name название.
 * @property icon ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewServerDto(val name: String, val icon: String? = null)