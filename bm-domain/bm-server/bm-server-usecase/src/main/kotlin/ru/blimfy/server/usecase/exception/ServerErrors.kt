package ru.blimfy.server.usecase.exception

/**
 * Сообщения ошибок при работе с серверами.
 *
 * @param msg текст ошибка.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ServerErrors(val msg: String) {
    /**
     * Участник сервера уже существует.
     */
    SERVER_MEMBER_ALREADY_EXISTS("Server member with serverId - '%s', and userId - '%s', is exists!"),

    /**
     * Сервер не найден по идентификатору.
     */
    SERVER_BY_ID_NOT_FOUND("Server with id - '%s', not found!"),

    /**
     * Канал сервера не найден по идентификатору.
     */
    CHANNEL_BY_ID_NOT_FOUND("Channel with id - '%s', not found!"),

    /**
     * Участник сервера не найден по идентификатору пользователя и сервера.
     */
    MEMBER_BY_USER_ID_AND_SERVER_ID_NOT_FOUND("Member with user id - '%s', and server id - '%s', not found!"),

    /**
     * Приглашение на сервер не найдено по идентификатору.
     */
    INVITE_BY_ID_NOT_FOUND("Invite with id - '%s', not found!"),

    /**
     * Текстовое сообщение не найдено по идентификатору.
     */
    TEXT_MESSAGE_BY_ID_NOT_FOUND("Text message with id - '%s', not found!"),

    /**
     * Доступ к редактированию сервера запрещён.
     */
    SERVER_MODIFY_ACCESS_DENIED("Modify access denied for server with id - '%s'!"),

    /**
     * Доступ к просмотру сервера запрещён.
     */
    SERVER_VIEW_ACCESS_DENIED("View access denied for server with id - '%s'!"),
}