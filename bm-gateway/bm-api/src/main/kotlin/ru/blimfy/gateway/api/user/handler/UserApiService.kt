package ru.blimfy.gateway.api.user.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.domain.channel.api.dto.channel.NewChannelDto
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.member.MemberDto
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.gateway.dto.user.UsernameDto

/**
 * Интерфейс для работы с обработкой запросов о пользователях.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface UserApiService {
    /**
     * Возвращает [modifyUser], обновляемого [user].
     */
    suspend fun modifyUser(modifyUser: ModifyUserDto, user: User): UserDto

    /**
     * Возвращает пользователя с обновлённым именем из [usernameInfo], обновляемого [user].
     */
    suspend fun changeUsername(usernameInfo: UsernameDto, user: User): UserDto

    /**
     * Возвращает информацию участника сервера с [serverId] для [user].
     */
    suspend fun findMember(serverId: UUID, user: User): MemberDto

    /**
     * Удаляет участие [user] с сервера с [serverId].
     */
    suspend fun leaveServer(serverId: UUID, user: User)

    /**
     * Возвращает личный [channelDto] для [user].
     */
    suspend fun createDmChannel(channelDto: NewChannelDto, user: User): ChannelDto

    /**
     * Возвращает личные диалоги [user].
     */
    fun findUserDmChannels(user: User): Flow<ChannelDto>
}