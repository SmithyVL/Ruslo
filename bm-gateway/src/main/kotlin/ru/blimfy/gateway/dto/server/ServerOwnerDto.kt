package ru.blimfy.gateway.dto.server

import java.util.UUID

/**
 * DTO с новым владельцем сервера.
 *
 * @property ownerId идентификатор владельца.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ServerOwnerDto(val ownerId: UUID)