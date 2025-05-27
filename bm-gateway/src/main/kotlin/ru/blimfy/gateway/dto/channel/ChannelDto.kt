package ru.blimfy.gateway.dto.channel

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.common.enumeration.ChannelTypes.TEXT
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
@Schema(description = "Информация о канале")
@JsonInclude(NON_NULL)
data class ChannelDto(
    val id: UUID,
    val type: ChannelTypes = TEXT,
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

/**
 * Возвращает DTO представление сущности канала.
 */
fun Channel.toDto() = ChannelDto(id, type, serverId, name, icon, ownerId, parentId, position, topic, nsfw)

/**
 * Возвращает DTO частичного представления сущности канала.
 */
fun Channel.toPartialDto() = ChannelPartialDto(id, type, name!!)