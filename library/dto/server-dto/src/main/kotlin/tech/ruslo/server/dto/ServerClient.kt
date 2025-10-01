package tech.ruslo.server.dto

import kotlinx.coroutines.flow.Flow

/**
 * Клиентский интерфейс для работы с информацией о серверах из сервиса серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerClient {
    /**
     * Возвращает сохранённый [serverDto].
     */
    suspend fun saveServer(serverDto: ServerDto): ServerDto

    /**
     * Возвращает сервера пользователя с идентификатором [userId].
     */
    fun getUserServers(userId: Long): Flow<ServerDto>
}