package ru.blimfy.domain.channel.usecase.exception

/**
 * Сообщения ошибок, связанных с информацией о каналах.
 *
 * @param msg текст ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ChannelErrors(val msg: String) {
    /**
     * Канал не найден.
     */
    CHANNEL_NOT_FOUND("Channel not found!"),

    /**
     * Сообщение не найдено.
     */
    MESSAGE_NOT_FOUND("Message not found!"),

    /**
     * В канале уже максимальное количество закреплённых сообщений.
     */
    PINNED_LIMIT("Maximum number (50) of pinned messages in the channel has been reached!"),

    /**
     * Приглашение на канал не найдено.
     */
    INVITE_NOT_FOUND("Invite not found!"),
}