package ru.blimfy.gateway.api.server.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.domain.channel.api.dto.channel.ChannelPositionDto
import ru.blimfy.domain.channel.api.dto.channel.NewChannelDto
import ru.blimfy.domain.server.api.dto.server.ModifyServerDto
import ru.blimfy.domain.server.api.dto.server.NewServerDto
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.invite.InviteDto
import ru.blimfy.gateway.dto.server.ServerDto

/**
 * Интерфейс для работы с обработкой запросов о серверах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerApiService {
    /**
     * Возвращает новый [newServerDto], который создаёт [user].
     */
    suspend fun createServer(newServerDto: NewServerDto, user: User): ServerDto

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
     * Возвращает [newChannelDto] на сервере с [id], который создаёт [user].
     */
    suspend fun createChannel(id: UUID, newChannelDto: NewChannelDto, user: User): ChannelDto

    /**
     * [user] изменяет [positions] каналов на сервере с [id].
     */
    suspend fun modifyChannelPositions(id: UUID, positions: List<ChannelPositionDto>, user: User)

    /**
     * Возвращает приглашения сервера с [id], которых хочет получить [user].
     */
    suspend fun findInvites(id: UUID, user: User): Flow<InviteDto>

    /**
     * Возвращает новое приглашение для сервера с [id] для канала с [channelId], созданного [user].
     */
    suspend fun createInvite(id: UUID, channelId: UUID, user: User): InviteDto
}