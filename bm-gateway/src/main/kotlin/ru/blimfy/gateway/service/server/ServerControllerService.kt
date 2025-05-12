package ru.blimfy.gateway.service.server

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.invite.InviteDto
import ru.blimfy.gateway.dto.member.MemberDetailsDto
import ru.blimfy.gateway.dto.server.NewServerDto
import ru.blimfy.gateway.dto.server.ServerDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * Интерфейс для работы с обработкой запросов о серверах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerControllerService {
    /**
     * Возвращает новый [newServerDto], который создаёт [user].
     */
    suspend fun createServer(newServerDto: NewServerDto, user: CustomUserDetails): ServerDto

    /**
     * Возвращает обновлённый [serverDto], который обновляет [user].
     */
    suspend fun modifyServer(serverDto: ServerDto, user: CustomUserDetails): ServerDto

    /**
     * Возвращает сервер с [serverId], который хочет получить [user].
     */
    suspend fun findServer(serverId: UUID, user: CustomUserDetails): ServerDto

    /**
     * Удаляет сервер с [serverId], который удаляет [user].
     */
    suspend fun deleteServer(serverId: UUID, user: CustomUserDetails)

    /**
     * Кикает участника с [memberId] с сервера с [serverId], которого кикает [user].
     */
    suspend fun deleteServerMember(serverId: UUID, memberId: UUID, user: CustomUserDetails)

    /**
     * Возвращает участников сервера с [serverId], которых хочет получить [user].
     */
    suspend fun findServerMembers(serverId: UUID, user: CustomUserDetails): Flow<MemberDetailsDto>

    /**
     * Возвращает каналы сервера с [serverId], которых хочет получить [user].
     */
    suspend fun findServerChannels(serverId: UUID, user: CustomUserDetails): Flow<ChannelDto>

    /**
     * Возвращает приглашения сервера с [serverId], которых хочет получить [user].
     */
    suspend fun findServerInvites(serverId: UUID, user: CustomUserDetails): Flow<InviteDto>
}