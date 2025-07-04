package ru.blimfy.gateway.dto.invite

import java.util.UUID
import ru.blimfy.domain.channel.db.entity.InviteTypes
import ru.blimfy.domain.channel.db.entity.InviteTypes.*
import ru.blimfy.gateway.dto.channel.PartialChannelDto
import ru.blimfy.gateway.dto.server.PartialServerDto
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
data class InviteDto(val id: UUID, val type: InviteTypes = SERVER) {
    lateinit var channel: PartialChannelDto
    lateinit var inviter: UserDto
    var server: PartialServerDto? = null
    var approximateMemberCount: Long? = null
}