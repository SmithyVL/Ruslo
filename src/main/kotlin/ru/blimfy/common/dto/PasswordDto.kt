package ru.blimfy.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID

/**
 * DTO с информацией о пароле пользователя.
 *
 * @property userId идентификатор пользователя.
 * @property hash хэш пароля.
 * @property id идентификатор.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class PasswordDto(
    val userId: UUID,
    val hash: String,
    val id: UUID? = null,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val createdDate: Instant? = null,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val updatedDate: Instant? = null,
)