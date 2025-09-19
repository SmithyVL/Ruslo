package tech.ruslo.gateway.dto.websockets

import java.util.UUID

/**
 * DTO с частичной информацией об участнике сервера.
 *
 * @property serverId идентификатор сервера.
 * @property nick ник.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class PartialMemberDto(val serverId: UUID, val nick: String? = null)