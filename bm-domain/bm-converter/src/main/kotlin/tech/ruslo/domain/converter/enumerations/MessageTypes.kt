package tech.ruslo.domain.converter.enumerations

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
     * Новый участник канала.
     */
    RECIPIENT_ADD,

    /**
     * Участник удалён из канала.
     */
    RECIPIENT_REMOVE,

    /**
     * Звонок.
     */
    CALL,

    /**
     * Название канала изменено.
     */
    CHANNEL_NAME_CHANGE,

    /**
     * Иконка канала изменена.
     */
    CHANNEL_ICON_CHANGE,

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