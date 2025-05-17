package ru.blimfy.gateway.service.user

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.dm.channel.DmChannelDto
import ru.blimfy.gateway.dto.server.ServerShortDto
import ru.blimfy.gateway.dto.server.member.MemberDto
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.gateway.dto.user.UsernameDto
import ru.blimfy.gateway.dto.user.friend.FriendDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о пользователях.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface UserControllerService {
    /**
     * Возвращает [modifyUser], обновляемого [currentUser].
     */
    suspend fun modifyUser(modifyUser: ModifyUserDto, currentUser: User): UserDto

    /**
     * Возвращает пользователя с обновлённым именем из [usernameInfo], обновляемого [currentUser].
     */
    suspend fun changeUsername(usernameInfo: UsernameDto, currentUser: User): UserDto

    /**
     * Возвращает сервера [currentUser].
     */
    fun findUserServers(currentUser: User): Flow<ServerShortDto>

    /**
     * Возвращает информацию об участии [currentUser] на сервере с [serverId].
     */
    suspend fun findServerMember(serverId: UUID, currentUser: User): MemberDto

    /**
     * Удаляет участие [currentUser] с сервера с [serverId].
     */
    suspend fun leaveServer(serverId: UUID, currentUser: User)

    /**
     * Возвращает личные диалоги [currentUser].
     */
    fun findUserDmChannels(currentUser: User): Flow<DmChannelDto>

    /**
     * Возвращает друзей [currentUser].
     */
    fun findUserFriends(currentUser: User): Flow<FriendDto>
}