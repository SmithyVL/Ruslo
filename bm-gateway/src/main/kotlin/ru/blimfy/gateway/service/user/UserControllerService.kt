package ru.blimfy.gateway.service.user

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.server.ServerPartialDto
import ru.blimfy.gateway.dto.server.member.MemberDto
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.gateway.dto.user.UsernameDto
import ru.blimfy.gateway.dto.user.channel.NewDmChannelDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о пользователях.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface UserControllerService {
    /**
     * Возвращает [modifyUser], обновляемого [user].
     */
    suspend fun modifyUser(modifyUser: ModifyUserDto, user: User): UserDto

    /**
     * Возвращает пользователя с обновлённым именем из [usernameInfo], обновляемого [user].
     */
    suspend fun changeUsername(usernameInfo: UsernameDto, user: User): UserDto

    /**
     * Возвращает сервера [user].
     */
    fun findUserServers(user: User): Flow<ServerPartialDto>

    /**
     * Возвращает информацию участника сервера с [serverId] для [user].
     */
    suspend fun findMember(serverId: UUID, user: User): MemberDto

    /**
     * Удаляет участие [user] с сервера с [serverId].
     */
    suspend fun leaveServer(serverId: UUID, user: User)

    /**
     * Возвращает личный [channel] для [user].
     */
    suspend fun createDmChannel(channel: NewDmChannelDto, user: User): ChannelDto

    /**
     * Возвращает личные диалоги [user].
     */
    fun findUserDmChannels(user: User): Flow<ChannelDto>
}