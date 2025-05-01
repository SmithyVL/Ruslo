package ru.blimfy.direct.usecase.exception

/**
 * Сообщения ошибок личных диалогов.
 *
 * @param msg текст ошибка.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class DirectErrors(val msg: String) {
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
     * Доступ к личному диалогу запрещён.
     */
    CONSERVATION_ACCESS_DENIED("Access denied for conservation with id - '%s'!"),
}