package ru.blimfy.gateway.api.server.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import ru.blimfy.gateway.api.dto.RoleDto
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.server.db.entity.Server
import kotlin.properties.Delegates
import kotlin.properties.Delegates.notNull

/**
 * DTO с информацией о сервере.
 *
 * @property id идентификатор.
 * @property name название.
 * @property ownerId идентификатор владельца сервера.
 * @property icon ссылка на файл аватарки.
 * @property bannerColor цвет баннера.
 * @property description описание сервера.
 * @property createdDate дата создания.
 * @property owner является ли пользователь владельцем сервера.
 * @property roles роли сервера.
 * @property permissions общие разрешения участника сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(NON_NULL)
data class ServerDto(
    val id: UUID,
    val name: String,
    val ownerId: UUID,
    val icon: String? = null,
    val bannerColor: String? = null,
    val description: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE) val createdDate: Instant,
) {
    var owner by notNull<Boolean>()
    lateinit var roles: List<RoleDto>
    lateinit var permissions: String
}