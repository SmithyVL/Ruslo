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
    EDIT_SERVER_MEMBER,

    /**
     * Участник удалён с сервера.
     */
    REMOVE_SERVER_MEMBER,

    /**
     * Создан новый канал на сервере.
     */
    NEW_SERVER_CHANNEL,

    /**
     * Удалён канал на сервере.
     */
    REMOVE_SERVER_CHANNEL,

    /**
     * Изменён канал на сервере.
     */
    EDIT_SERVER_CHANNEL,

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
     * Создано новое сообщение в личном диалоге.
     */
    NEW_DIRECT_MESSAGE,

    /**
     * Удалено сообщение в личном диалоге.
     */
    REMOVE_DIRECT_MESSAGE,

    /**
     * Изменено сообщение в личном диалоге.
     */
    EDIT_DIRECT_MESSAGE,

    /**
     * Закреплено сообщение в личном диалоге.
     */
    @Suppress("unused")
    PINNED_DIRECT_MESSAGE,
}