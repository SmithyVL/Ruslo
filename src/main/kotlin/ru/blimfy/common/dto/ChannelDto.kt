package ru.blimfy.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.common.enums.ChannelTypes

/**
 * DTO с информацией о канале.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property type тип.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ChannelDto(
    val id: UUID? = null,
    val serverId: UUID,
    val name: String,
    val type: ChannelTypes,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val createdDate: Instant? = null,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val updatedDate: Instant? = null,
)
