package ru.blimfy.websocket.dto

/**
 * Типы сообщений, отправляемых через WebSocket соединения.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class WsMessageTypes {
    /**
     * Сервер изменён.
     */
    EDIT_SERVER,

    /**
     * Новый участник присоединился к серверу.
     */
    NEW_SERVER_MEMBER,

    /**
     * Участник сервера изменён.
     */
    GUILD_MEMBER_UPDATE,

    /**
     * Участник удалён с сервера.
     */
    REMOVE_SERVER_MEMBER,

    /**
     * Создан новый канал на сервере.
     */
    CHANNEL_CREATE,

    /**
     * Удалён канал на сервере.
     */
    CHANNEL_DELETE,

    /**
     * Изменён канал на сервере.
     */
    CHANNEL_UPDATE,

    /**
     * Создано новое сообщение в канале сервера.
     */
    NEW_TEXT_MESSAGE,

    /**
     * Удалено сообщение в канале сервера.
     */
    REMOVE_TEXT_MESSAGE,

    /**
     * Изменено сообщение в канале сервера.
     */
    EDIT_TEXT_MESSAGE,

    /**
     * Закреплено сообщение в канале сервера.
     */
    @Suppress("unused")
    PINNED_TEXT_MESSAGE,

    /**
     * Создано новое сообщение в личном канале.
     */
    NEW_DM_MESSAGE,

    /**
     * Удалено сообщение в личном канале.
     */
    DELETE_DM_MESSAGE,

    /**
     * Изменено сообщение в личном канале.
     */
    EDIT_DM_MESSAGE,

    /**
     * Закреплено сообщение в личном канале.
     */
    PINNED_DM_MESSAGE,
}