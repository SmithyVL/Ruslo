package tech.ruslo.channel.dto.message.enumerations

/**
 * Типы сообщений каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Suppress("unused")
enum class MessageTypes {
    /**
     * Стандартное сообщение.
     */
    DEFAULT,

    /**
     * Звонок.
     */
    CALL,

    /**
     * Сообщение в канале закреплено.
     */
    CHANNEL_PINNED_MESSAGE,

    /**
     * Пользователь присоединился к серверу.
     */
    USER_JOIN,

    /**
     * Ответ на сообщение.
     */
    REPLY,
}