package ru.blimfy.gateway.service.server

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.invite.InviteDto
import ru.blimfy.gateway.dto.server.ModifyServerDto
import ru.blimfy.gateway.dto.server.NewServerDto
import ru.blimfy.gateway.dto.server.ServerDto
import ru.blimfy.gateway.dto.server.channel.ChannelPositionDto
import ru.blimfy.gateway.dto.server.channel.ServerChannelDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о серверах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerControllerService {
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
     * Возвращает новый [channel] на сервере с [id], который создаёт [user].
     */
    suspend fun createChannel(id: UUID, channel: ServerChannelDto, user: User): ChannelDto

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