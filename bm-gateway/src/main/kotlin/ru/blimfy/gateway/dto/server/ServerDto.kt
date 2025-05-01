package ru.blimfy.gateway.dto.server

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.server.db.entity.Server

/**
 * DTO с информацией о сервере.
 *
 * @property id идентификатор.
 * @property ownerId идентификатор пользователя, владеющего сервером.
 * @property name название.
 * @property createdDate дата создания.
 * @property avatarUrl ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ServerDto(
    val id: UUID,
    val ownerId: UUID,
    val name: String,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC") val createdDate: Instant,
) {
    var avatarUrl: String? = null
}

/**
 * Возвращает сущность сервера из DTO.
 */
fun ServerDto.toEntity(ownerId: UUID) = Server(ownerId, name).apply {
    id = this@toEntity.id
    createdDate = this@toEntity.createdDate
    avatarUrl = this@toEntity.avatarUrl
}

/**
 * Возвращает DTO представление сущности сервера.
 */
fun Server.toDto() = ServerDto(id, ownerId, name, createdDate)
    .apply { this.avatarUrl = this@toDto.avatarUrl }