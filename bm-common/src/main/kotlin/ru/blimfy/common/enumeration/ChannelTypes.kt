package ru.blimfy.common.enumeration

import ru.blimfy.common.enumeration.ChannelGroups.SERVER
import ru.blimfy.common.enumeration.ChannelGroups.USER

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