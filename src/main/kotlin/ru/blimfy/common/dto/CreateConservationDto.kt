package ru.blimfy.common.dto

import java.util.UUID

/**
 * DTO с информацией о пользователях, участвующих в новом диалоге.
 *
 * @property firstUserId идентификатор первого пользователя.
 * @property secondUserId идентификатор второго пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class CreateConservationDto(val firstUserId: UUID, val secondUserId: UUID)
