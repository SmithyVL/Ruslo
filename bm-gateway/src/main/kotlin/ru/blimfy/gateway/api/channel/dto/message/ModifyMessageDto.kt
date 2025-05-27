package ru.blimfy.gateway.api.channel.dto.message

/**
 * DTO с обновлённой информацией о сообщении.
 *
 * @property content содержимое сообщения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyMessageDto(val content: String)