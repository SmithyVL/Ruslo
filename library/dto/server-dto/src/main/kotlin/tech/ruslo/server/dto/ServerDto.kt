package tech.ruslo.server.dto

/**
 * DTO с информацией о сервере.
 *
 * @property id идентификатор.
 * @property name название.
 * @property ownerId идентификатор владельца сервера.
 * @property defaultRoleId идентификатор стандартной роли сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ServerDto(val id: Long? = null, val name: String, val ownerId: Long, val defaultRoleId: Long? = null)