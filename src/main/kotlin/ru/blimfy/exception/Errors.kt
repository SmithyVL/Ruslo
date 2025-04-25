package ru.blimfy.exception

/**
 * Сообщения ошибок.
 *
 * @param msg текст ошибка.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class Errors(val msg: String) {
    /**
     * Пользователь уже существует.
     */
    USER_ALREADY_EXISTS("User with username - '%s', is exists!"),

    /**
     * Участник сервера уже существует.
     */
    SERVER_MEMBER_ALREADY_EXISTS("Server member with serverId - '%s', and userId - '%s', is exists!"),

    /**
     * Неверный пароль.
     */
    INCORRECT_PASSWORD("Incorrect password - '%s', for %s!"),

    /**
     * Пользователь не найден по идентификатору.
     */
    USER_BY_ID_NOT_FOUND("User with id - '%s', not found!"),

    /**
     * Пользователь не найден по имени пользователя.
     */
    USER_BY_USERNAME_NOT_FOUND("User with username - '%s', not found!"),

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
     * Пароль не найден по идентификатору пользователя.
     */
    PASSWORD_BY_USER_ID_NOT_FOUND("Password with user id - '%s', not found!"),

    /**
     * Приглашение на сервер не найдено по идентификатору.
     */
    INVITE_BY_ID_NOT_FOUND("Invite with id - '%s', not found!"),

    /**
     * Личный диалог пользователя не найден по идентификатору.
     */
    CONSERVATION_BY_ID_NOT_FOUND("Conservation with id - '%s', not found!"),

    /**
     * Участие пользователя в личном диалоге не найдено по идентификатору личного диалога.
     */
    MEMBER_CONSERVATION_BY_ID_NOT_FOUND("Member conservation with conservation id - '%s', not found!"),

    /**
     * Личное сообщение не найдено по идентификатору.
     */
    DIRECT_MESSAGE_BY_ID_NOT_FOUND("Direct message with id - '%s', not found!"),

    /**
     * Текстовое сообщение не найдено по идентификатору.
     */
    TEXT_MESSAGE_BY_ID_NOT_FOUND("Text message with id - '%s', not found!"),

    /**
     * Доступ к личному диалогу запрещён.
     */
    CONSERVATION_ACCESS_DENIED("Access denied for conservation with id - '%s'!"),

    /**
     * Доступ к редактированию сервера запрещён.
     */
    SERVER_MODIFY_ACCESS_DENIED("Modify access denied for server with id - '%s'!"),

    /**
     * Доступ к просмотру сервера запрещён.
     */
    SERVER_VIEW_ACCESS_DENIED("View access denied for server with id - '%s'!"),
}