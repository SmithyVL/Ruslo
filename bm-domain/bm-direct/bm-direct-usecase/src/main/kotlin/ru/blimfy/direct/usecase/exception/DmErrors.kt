package ru.blimfy.direct.usecase.exception

/**
 * Сообщения ошибок личных диалогов.
 *
 * @param msg текст ошибка.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class DmErrors(val msg: String) {
    /**
     * Личный канал пользователя не найден по идентификатору.
     */
    DM_CHANNEL_BY_ID_NOT_FOUND("Dm channel with id - '%s', not found!"),

    /**
     * Доступ к просмотру личного канала запрещён.
     */
    DM_CHANNEL_VIEW_ACCESS_DENIED("View access denied for dm channel with id - '%s'!"),

    /**
     * Доступ к редактированию группы запрещён.
     */
    GROUP_DM_MODIFY_ACCESS_DENIED("Modify access denied for group dm with id - '%s'!"),

    /**
     * Личное сообщение не найдено по идентификатору.
     */
    DM_MESSAGE_BY_ID_NOT_FOUND("DmMessage with id - '%s', not found!"),
}