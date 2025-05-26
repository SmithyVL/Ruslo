package ru.blimfy.gateway.service.channel.group

import java.util.UUID
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.invite.InviteDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о группах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface GroupControllerService {
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
    suspend fun createGroupInvite(id: UUID, user: User): InviteDto
}