package ru.blimfy.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID

/**
 * DTO с информацией о пользователе.
 *
 * @property id идентификатор.
 * @property username логин.
 * @property email электронная почта.
 * @property createdDate дата создания в UTC.
 * @property avatarUrl ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class UserDto(
    val id: UUID,
    val username: String,
    val email: String,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val createdDate: Instant,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val updatedDate: Instant? = null,

    val avatarUrl: String? = null
)