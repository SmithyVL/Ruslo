package ru.blimfy.gateway.dto.channel.invite

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.util.UUID
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.common.enumeration.InviteTypes
import ru.blimfy.gateway.dto.channel.ChannelPartialDto
import ru.blimfy.gateway.dto.server.ServerPartialDto
import ru.blimfy.gateway.dto.user.UserDto

/**
 * DTO с информацией о приглашении на канал.
 *
 * @property id идентификатор.
 * @property type тип.
 * @property channel частичная информация о канале.
 * @property inviter информация о приглашающем.
 * @property server частичная информация о сервере.
 * @property approximateMemberCount приблизительное количество участников на сервере.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(NON_NULL)
data class InviteDto(val id: UUID, val type: InviteTypes) {
    lateinit var channel: ChannelPartialDto
    lateinit var inviter: UserDto
    var server: ServerPartialDto? = null
    var approximateMemberCount: Long? = null
}

/**
 * Возвращает DTO представление сущности приглашения канала.
 */
fun Invite.toDto() = InviteDto(id = id, type = type)