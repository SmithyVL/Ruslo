package ru.blimfy.gateway.api.channel.group.handler

import java.util.UUID
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.invite.InviteDto

/**
 * Интерфейс для работы с обработкой запросов о группах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface GroupApiService {
    /**
     * [user] передаёт владение группой с [id] новому пользователю с [ownerId].
     */
    suspend fun changeOwner(id: UUID, ownerId: UUID, user: User): ChannelDto

    /**
     * [user] добавляет новых [recipients] в группу с [id].
     */
    suspend fun addRecipients(id: UUID, recipients: Set<UUID>, user: User): ChannelDto

    /**
     * [user] удаляет пользователя с [userId] из группы с [id].
     */
    suspend fun removeRecipient(id: UUID, userId: UUID, user: User): ChannelDto

    /**
     * Возвращает новое приглашение для группы с [id], созданного [user].
     */
    suspend fun createInvite(id: UUID, user: User): InviteDto
}