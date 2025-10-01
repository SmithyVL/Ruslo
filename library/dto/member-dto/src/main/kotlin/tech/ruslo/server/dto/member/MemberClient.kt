package tech.ruslo.server.dto.member

/**
 * Клиентский интерфейс для работы с информацией об участниках серверов из сервиса серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
fun interface MemberClient {
    /**
     * Удаляет участника для пользователя с [userId] и сервера с [serverId].
     */
    suspend fun removeMember(userId: Long, serverId: Long)
}