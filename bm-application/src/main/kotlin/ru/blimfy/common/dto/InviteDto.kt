package ru.blimfy.common.dto

import java.util.UUID

/**
 * DTO с информацией о приглашении на сервер.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property authorId автор приглашения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class InviteDto(val id: UUID? = null, val serverId: UUID, val authorId: UUID)
