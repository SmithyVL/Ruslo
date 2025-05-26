package ru.blimfy.channel.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.channel.db.entity.base.BaseEntity
import ru.blimfy.common.enumeration.InviteTypes

/**
 * Сущность, которая хранит в себе информацию о приглашении на канал.
 *
 * @property authorId идентификатор создателя приглашения.
 * @property channelId идентификатор канала.
 * @property type тип.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Invite(val authorId: UUID, val channelId: UUID, val type: InviteTypes) : BaseEntity() {
    var serverId: UUID? = null
}