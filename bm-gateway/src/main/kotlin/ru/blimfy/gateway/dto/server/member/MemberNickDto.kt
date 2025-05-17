package ru.blimfy.gateway.dto.server.member

import java.util.UUID

/**
 * DTO с информацией о новом нике участнику сервера.
 *
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property nick новый ник.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MemberNickDto(val serverId: UUID, val userId: UUID, val nick: String? = null)