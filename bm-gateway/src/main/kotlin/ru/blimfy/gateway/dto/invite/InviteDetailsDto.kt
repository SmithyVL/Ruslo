package ru.blimfy.gateway.dto.invite

import java.util.UUID

/**
 * DTO с подробной информацией о приглашении на сервер.
 *
 * @property inviteId идентификатор приглашения.
 * @property serverName название сервера.
 * @property serverAvatar аватар сервера.
 * @property countMembers количество участников на сервере.
 * @property referrerUsername ник пригласившего.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class InviteDetailsDto(
    val inviteId: UUID,
    val serverName: String,
    val serverAvatar: String? = null,
    val countMembers: Long,
    val referrerUsername: String,
)