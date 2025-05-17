package ru.blimfy.gateway.dto.server

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.server.db.entity.Server

/**
 * DTO с информацией о сервере.
 *
 * @property id идентификатор.
 * @property ownerId идентификатор владельца сервера.
 * @property name название.
 * @property icon ссылка на файл аватарки.
 * @property bannerColor цвет баннера.
 * @property description описание сервера.
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ServerDto(
    val id: UUID,
    val ownerId: UUID,
    val name: String,
    val icon: String? = null,
    val bannerColor: String? = null,
    val description: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE) val createdDate: Instant,
)

/**
 * Возвращает сущность сервера из DTO.
 */
fun ServerDto.toEntity(ownerId: UUID) = Server(ownerId, name).apply {
    id = this@toEntity.id
    icon = this@toEntity.icon
    bannerColor = this@toEntity.bannerColor
    createdDate = this@toEntity.createdDate
}

/**
 * Возвращает DTO представление сущности сервера.
 */
fun Server.toDto() = ServerDto(id, ownerId, name, icon, bannerColor, description, createdDate)