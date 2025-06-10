package ru.blimfy.gateway.api.server.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.api.dto.InviteDto
import ru.blimfy.gateway.api.dto.channel.ChannelDto
import ru.blimfy.gateway.api.dto.channel.NewChannelDto
import ru.blimfy.gateway.api.server.dto.ModifyServerDto
import ru.blimfy.gateway.api.server.dto.NewServerDto
import ru.blimfy.gateway.api.server.dto.ServerDto
import ru.blimfy.gateway.api.server.dto.channel.ChannelPositionDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о серверах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerApiService {
    /**
     * Возвращает новый [newServer], который создаёт [user].
     */
    suspend fun createServer(newServer: NewServerDto, user: User): ServerDto

    /**
     * Возвращает сервер с [id], который хочет получить [user].
     */
    suspend fun findServer(id: UUID, user: User): ServerDto

    /**
     * Возвращает обновлённый [modifyServer] с [id], который обновляет [user].
     */
    suspend fun modifyServer(id: UUID, modifyServer: ModifyServerDto, user: User): ServerDto

    /**
     * Возвращает обновлённый сервер с [id] и новым владельцем с [userId], который обновляет [user].
     */
    suspend fun changeOwner(id: UUID, userId: UUID, user: User): ServerDto

    /**
     * Удаляет сервер с [id], который удаляет [user].
     */
    suspend fun deleteServer(id: UUID, user: User)

    /**
     * Возвращает каналы сервера с [id], которых хочет получить [user].
     */
    suspend fun findServerChannels(id: UUID, user: User): Flow<ChannelDto>

    /**
     * Возвращает новый [channelDto] на сервере с [id], который создаёт [user].
     */
    suspend fun createChannel(id: UUID, channelDto: NewChannelDto, user: User): ChannelDto

    /**
     * [user] изменяет [positions] каналов на сервере с [id].
     */
    suspend fun modifyServerChannelPositions(id: UUID, positions: List<ChannelPositionDto>, user: User)

    /**
     * Возвращает приглашения сервера с [id], которых хочет получить [user].
     */
    suspend fun findServerInvites(id: UUID, user: User): Flow<InviteDto>

    /**
     * Возвращает новое приглашение для сервера с [id] для канала с [channelId], созданного [user].
     */
    suspend fun createInvite(id: UUID, channelId: UUID, user: User): InviteDto
}