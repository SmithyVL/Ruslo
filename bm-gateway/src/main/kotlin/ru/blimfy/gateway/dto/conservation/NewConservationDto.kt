package ru.blimfy.gateway.dto.conservation

import java.util.UUID

/**
 * DTO с информацией о пользователях, участвующих в новом диалоге.
 *
 * @property firstUserId идентификатор первого пользователя.
 * @property secondUserId идентификатор второго пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewConservationDto(val firstUserId: UUID, val secondUserId: UUID)
