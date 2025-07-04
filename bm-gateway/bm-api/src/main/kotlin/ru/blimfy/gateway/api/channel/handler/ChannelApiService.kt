package ru.blimfy.gateway.api.channel.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.domain.channel.api.dto.channel.ModifyChannelDto
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.invite.InviteDto

/**
 * Интерфейс для работы с обработкой запросов о каналах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelApiService {
    /**
     * Возвращает обновлённый [modifyChannel] с [id], который обновляет [user].
     */
    suspend fun modifyChannel(id: UUID, modifyChannel: ModifyChannelDto, user: User): ChannelDto

    /**
     * Удаляет канал с [id], который удаляет [user].
     */
    suspend fun deleteChannel(id: UUID, user: User)

    /**
     * Отправляет событие о начале набора текста [user] в канал с [id].
     */
    suspend fun triggerTypingIndicator(id: UUID, user: User)

    /**
     * Возвращает приглашения канала с [id], которые хочет получить [user].
     */
    suspend fun findInvites(id: UUID, user: User): Flow<InviteDto>
}