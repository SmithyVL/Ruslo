package tech.ruslo.dto.websocket

/**
 * Типы сообщений, отправляемых/получаемых через WebSocket соединения.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class SendEvents {
    /**
     * Содержит информацию о начальном состоянии.
     */
    READY,

    /**
     * Канал создан.
     */
    CHANNEL_CREATE,

    /**
     * Канал обновлён.
     */
    CHANNEL_UPDATE,

    /**
     * Канал удалён.
     */
    CHANNEL_DELETE,

    /**
     * Сообщение канала было закреплено или откреплено.
     */
    CHANNEL_PINS_UPDATE,

    /**
     * Сервер добавлен к пользователю.
     */
    SERVER_CREATE,

    /**
     * Сервер обновлён.
     */
    SERVER_UPDATE,

    /**
     * Сервер удалён.
     */
    SERVER_DELETE,

    /**
     * Бан сервера создан.
     */
    SERVER_BAN_ADD,

    /**
     * Бан сервера удалён.
     */
    SERVER_BAN_REMOVE,

    /**
     * К серверу присоединился новый пользователь.
     */
    SERVER_MEMBER_ADD,

    /**
     * Пользователь был удалён с сервера.
     */
    SERVER_MEMBER_REMOVE,

    /**
     * Участник сервера был обновлён.
     */
    SERVER_MEMBER_UPDATE,

    /**
     * Роль сервера была создана.
     */
    SERVER_ROLE_CREATE,

    /**
     * Роль сервера была обновлена.
     */
    SERVER_ROLE_UPDATE,

    /**
     * Роль сервера была удалена.
     */
    SERVER_ROLE_DELETE,

    /**
     * Приглашение в канал было создано.
     */
    INVITE_CREATE,

    /**
     * Приглашение в канал было удалено.
     */
    INVITE_DELETE,

    /**
     * Сообщение создано.
     */
    MESSAGE_CREATE,

    /**
     * Сообщение обновлено.
     */
    MESSAGE_UPDATE,

    /**
     * Сообщение удалено.
     */
    MESSAGE_DELETE,

    /**
     * Пользователь начал печатать в канале.
     */
    TYPING_START,

    /**
     * Пользователь был обновлён.
     */
    USER_UPDATE,

    /**
     * Кто-то присоединился, вышел или переместился в голосовом канале.
     */
    VOICE_STATE_UPDATE,
}