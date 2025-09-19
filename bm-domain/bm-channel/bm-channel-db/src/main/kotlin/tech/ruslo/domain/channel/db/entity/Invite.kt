package tech.ruslo.domain.channel.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.domain.channel.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о приглашении на канал.
 *
 * @property authorId идентификатор создателя приглашения.
 * @property channelId идентификатор канала.
 * @property type тип.
 * @property serverId идентификатор сервера.
 * @property channel канал приглашения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Invite(val authorId: UUID, val channelId: UUID, val type: InviteTypes) : BaseEntity() {
    var serverId: UUID? = null

    lateinit var channel: Channel
}

/**
 * Типы приглашений.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Suppress("unused")
enum class InviteTypes {
    /**
     * Приглашение на сервер.
     */
    SERVER,

    /**
     * Приглашение в группу.
     */
    GROUP_DM,
}