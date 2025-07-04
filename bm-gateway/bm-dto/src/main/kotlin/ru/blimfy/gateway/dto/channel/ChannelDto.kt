package ru.blimfy.gateway.dto.channel

import java.util.UUID
import ru.blimfy.gateway.dto.user.UserDto

/**
 * DTO с информацией о канале.
 *
 * @property id идентификатор.
 * @property type тип.
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property icon иконка группы.
 * @property ownerId идентификатор создателя группы.
 * @property parentId идентификатор родительского канала.
 * @property position номер сортировки каналов внутри сервера.
 * @property nsfw является ли канал NSFW.
 * @property topic тема.
 * @property recipients идентификаторы участников.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ChannelDto(
    val id: UUID,
    val type: String,
    val serverId: UUID? = null,
    val name: String? = null,
    val icon: String? = null,
    val ownerId: UUID? = null,
    val parentId: UUID? = null,
    val position: Long? = null,
    val topic: String? = null,
    val nsfw: Boolean? = null,
) {
    var recipients: List<UserDto>? = null
}