package ru.blimfy.gateway.service.user

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.conservation.ConservationTitleDto
import ru.blimfy.gateway.dto.server.ServerDto
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

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
    suspend fun modifyUser(modifyUser: ModifyUserDto, user: CustomUserDetails): UserDto

    /**
     * Возвращает сервера [user].
     */
    fun findUserServers(user: CustomUserDetails): Flow<ServerDto>

    /**
     * Возвращает личные диалоги [user].
     */
    fun findUserConservations(user: CustomUserDetails): Flow<ConservationTitleDto>

    /**
     * Удаляет участие [user] с сервера с [serverId].
     */
    suspend fun leaveServer(serverId: UUID, user: CustomUserDetails)
}