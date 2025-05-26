package ru.blimfy.gateway.service.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.ModifyChannelDto
import ru.blimfy.gateway.dto.channel.invite.InviteDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о каналах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelControllerService {
    /**
     * Возвращает канал с [id], который хочет получить [user].
     */
    suspend fun findChannel(id: UUID, user: User): ChannelDto

    /**
     * Возвращает обновлённый [modifyChannel] с [id], который обновляет [user].
     */
    suspend fun modifyChannel(id: UUID, modifyChannel: ModifyChannelDto, user: User): ChannelDto

    /**
     * Удаляет канал с [id], который удаляет [user].
     */
    suspend fun deleteChannel(id: UUID, user: User): ChannelDto

    /**
     * Отправляет событие о начале набора текста [user] в канал с [id].
     */
    suspend fun triggerTypingIndicator(id: UUID, user: User)

    /**
     * Возвращает приглашения канала с [id], которые хочет получить [user].
     */
    suspend fun findInvites(id: UUID, user: User): Flow<InviteDto>

    /**
     * Возвращает новое приглашение для группы с [id], созданного [user].
     */
    suspend fun createGroupInvite(id: UUID, user: User): InviteDto
}