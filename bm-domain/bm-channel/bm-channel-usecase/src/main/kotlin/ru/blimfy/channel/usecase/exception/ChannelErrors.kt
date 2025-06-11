package ru.blimfy.channel.usecase.exception

/**
 * Сообщения ошибок, связанных с информацией о каналах.
 *
 * @param msg текст ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ChannelErrors(val msg: String) {
    /**
     * Канал не найден по идентификатору.
     */
    CHANNEL_BY_ID_NOT_FOUND("Channel with id - '%s', not found!"),

    /**
     * Сообщение не найдено по идентификатору.
     */
    MESSAGE_BY_ID_NOT_FOUND("Message with id - '%s', not found!"),

    /**
     * В канале уже максимальное количество закреплённых сообщений.
     */
    MESSAGE_PINNED_LIMIT("Maximum number (50) of pinned messages in the channel ('%s') has been reached!"),

    /**
     * Приглашение на канал не найдено по идентификатору.
     */
    INVITE_BY_ID_NOT_FOUND("Invite with id - '%s', not found!"),
}