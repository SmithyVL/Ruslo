package ru.blimfy.common.dto.server

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.Application.Companion.INSTANT_FORMAT

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