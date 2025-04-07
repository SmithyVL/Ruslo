package ru.blimfy.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID

/**
 * DTO с информацией о сервере для его создания/обновления.
 *
 * @property id идентификатор.
 * @property ownerId идентификатор пользователя, владеющего сервером.
 * @property name название.
 * @property avatarUrl ссылка на файл аватарки.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ServerDto(
    val id: UUID? = null,
    val ownerId: UUID,
    val name: String,
    val avatarUrl: String? = null,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val createdDate: Instant? = null,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val updatedDate: Instant? = null,
)