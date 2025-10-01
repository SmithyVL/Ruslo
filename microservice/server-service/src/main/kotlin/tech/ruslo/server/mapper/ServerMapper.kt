package tech.ruslo.server.mapper

import tech.ruslo.server.database.entity.Server
import tech.ruslo.server.dto.ServerDto

/**
 * Возвращает сущность сервера из DTO представления сервера.
 */
fun ServerDto.toEntity() = Server(ownerId, name).apply {
    this@toEntity.id?.let { id = it }
}

/**
 * Возвращает DTO представление сервера с информацией из сущности сервера.
 */
fun Server.toDto() = ServerDto(id, name, ownerId)