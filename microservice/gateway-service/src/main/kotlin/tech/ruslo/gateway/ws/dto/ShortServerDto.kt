package tech.ruslo.gateway.ws.dto

/**
 * DTO краткой информацией о сервере.
 *
 * @property id идентификатор сервера.
 * @property name название сервера.
 * @property owner является ли пользователь владельцем сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ShortServerDto(val id: Long, val name: String, val owner: Boolean)