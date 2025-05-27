package ru.blimfy.gateway.api.server.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
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
@JsonInclude(NON_NULL)
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
 * Возвращает DTO представление сущности сервера.
 */
fun Server.toDto() = ServerDto(id, ownerId, name, icon, bannerColor, description, createdDate)