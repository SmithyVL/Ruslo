package ru.blimfy.gateway.service.server

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.server.ModifyServerDto
import ru.blimfy.gateway.dto.server.NewServerDto
import ru.blimfy.gateway.dto.server.ServerDto
import ru.blimfy.gateway.dto.server.ServerOwnerDto
import ru.blimfy.gateway.dto.server.channel.ChannelDto
import ru.blimfy.gateway.dto.server.invite.InviteDto
import ru.blimfy.gateway.dto.server.member.MemberDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о серверах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerControllerService {
    /**
     * Возвращает новый [newServer], который создаёт [currentUser].
     */
    suspend fun createServer(newServer: NewServerDto, currentUser: User): ServerDto

    /**
     * Возвращает обновлённый [modifyServer] с [serverId], который обновляет [currentUser].
     */
    suspend fun modifyServer(serverId: UUID, modifyServer: ModifyServerDto, currentUser: User): ServerDto

    /**
     * Возвращает обновлённый сервер с [serverId] и новым [serverOwner], который обновляет [currentUser].
     */
    suspend fun changeOwner(serverId: UUID, serverOwner: ServerOwnerDto, currentUser: User): ServerDto

    /**
     * Возвращает сервер с [serverId], который хочет получить [currentUser].
     */
    suspend fun findServer(serverId: UUID, currentUser: User): ServerDto

    /**
     * Удаляет сервер с [serverId], который удаляет [currentUser].
     */
    suspend fun deleteServer(serverId: UUID, currentUser: User)

    /**
     * Кикает участника с [memberId] с сервера с [serverId], которого кикает [currentUser].
     */
    suspend fun deleteServerMember(serverId: UUID, memberId: UUID, currentUser: User)

    /**
     * Возвращает участников сервера с [serverId], которых хочет получить [currentUser].
     */
    suspend fun findServerMembers(serverId: UUID, currentUser: User): Flow<MemberDto>

    /**
     * Возвращает каналы сервера с [serverId], которых хочет получить [currentUser].
     */
    suspend fun findServerChannels(serverId: UUID, currentUser: User): Flow<ChannelDto>

    /**
     * Возвращает приглашения сервера с [serverId], которых хочет получить [currentUser].
     */
    suspend fun findServerInvites(serverId: UUID, currentUser: User): Flow<InviteDto>
}