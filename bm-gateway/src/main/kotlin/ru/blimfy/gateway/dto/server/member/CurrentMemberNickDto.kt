package ru.blimfy.gateway.dto.server.member

import java.util.UUID

/**
 * DTO с информацией о новом нике участника сервера.
 *
 * @property serverId идентификатор сервера.
 * @property nick новый ник.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class CurrentMemberNickDto(val serverId: UUID, val nick: String? = null)