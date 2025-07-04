package ru.blimfy.domain.channel.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.domain.channel.db.entity.ChannelGroups.SERVER
import ru.blimfy.domain.channel.db.entity.ChannelGroups.USER
import ru.blimfy.domain.channel.db.entity.ChannelTypes.GROUP_DM
import ru.blimfy.domain.channel.db.entity.base.BaseEntity

/**
 * Сущность с информацией о канале.
 *
 * @property type тип.
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property icon иконка группы.
 * @property ownerId идентификатор создателя группы.
 * @property recipients идентификаторы участников.
 * @property parentId идентификатор родительского канала.
 * @property position номер сортировки каналов внутри сервера.
 * @property topic тема.
 * @property nsfw является ли канал NSFW.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class Channel(val type: ChannelTypes = GROUP_DM, val serverId: UUID? = null) : BaseEntity() {
    var name: String? = null
    var icon: String? = null
    var ownerId: UUID? = null
    var recipients: Set<UUID>? = null
    var parentId: UUID? = null
    var position: Long? = null
    var topic: String? = null
    var nsfw: Boolean? = null
}

/**
 * Типы каналов.
 *
 * @param group группа канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ChannelTypes(val group: ChannelGroups) {
    /**
     * Текстовый канал.
     */
    TEXT(SERVER),

    /**
     * Личный диалог.
     */
    DM(USER),

    /**
     * Голосовой канал.
     */
    VOICE(SERVER),

    /**
     * Группа.
     */
    GROUP_DM(USER),

    /**
     * Категория сервера.
     */
    CATEGORY(SERVER),

    /**
     * Канал, на который пользователи могут подписаться и размещать его посты на своём сервере.
     */
    ANNOUNCEMENT(SERVER),

    /**
     * Временный подканал в рамках канала ANNOUNCEMENT.
     */
    ANNOUNCEMENT_THREAD(SERVER),

    /**
     * Временный подканал в рамках канала TEXT или FORUM.
     */
    PUBLIC_THREAD(SERVER),

    /**
     * Временный подканал в рамках канала TEXT или FORUM.
     */
    PRIVATE_THREAD(SERVER),

    /**
     * Голосовой канал для проведения мероприятий с аудиторией.
     */
    STAGE_VOICE(SERVER),

    /**
     * Канал в ученическом хабе, содержащем серверы.
     */
    DIRECTORY(USER),

    /**
     * Канал, который может содержать только ветки - PUBLIC_THREAD.
     */
    FORUM(SERVER),

    /**
     * Канал, который может содержать только ветки - PUBLIC_THREAD. Аналогично, GUILD_FORUM каналам.
     */
    MEDIA(SERVER),
}

/**
 * Группы каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ChannelGroups {
    /**
     * Личные каналы пользователя.
     */
    USER,

    /**
     * Серверные каналы.
     */
    SERVER,
}