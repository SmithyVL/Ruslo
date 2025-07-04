package ru.blimfy.gateway.access.control.service

import java.util.UUID

/**
 * Интерфейс для работы с разрешениями, токенами и шифрованием/дешифрованием паролей.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface AccessService {
    /**
     * Есть ли у пользователя с [userId] доступ к просмотру канала с [channelId].
     */
    suspend fun isChannelViewAccess(channelId: UUID, userId: UUID)

    /**
     * Есть ли у пользователя с [userId] доступ к редактированию канала с [channelId].
     */
    suspend fun isChannelWriteAccess(channelId: UUID, userId: UUID)

    /**
     * Есть ли у пользователя с [userId] доступ к закреплению сообщений в канале с [channelId].
     */
    suspend fun isPinsWriteAccess(channelId: UUID, userId: UUID)

    /**
     * Является ли пользователь с [userId] владельцем сервера с [serverId].
     */
    suspend fun isServerOwner(serverId: UUID, userId: UUID)

    /**
     * Является ли пользователь с [userId] участником сервера с [serverId].
     */
    suspend fun isServerMember(serverId: UUID, userId: UUID)

    /**
     * Является ли пользователь с [userId] заблокированным на сервере с [serverId].
     */
    suspend fun hasServerBan(serverId: UUID, userId: UUID)

    /**
     * Проверяет совпадение [checkPassword] с текущим [password].
     */
    fun checkPassword(checkPassword: String, password: String)

    /**
     * Возвращает хэш для [password].
     */
    fun encodePassword(password: String): String

    /**
     * Возвращает токен авторизации для пользователя с [username] и [userId].
     */
    fun generateToken(username: String, userId: UUID): String
}