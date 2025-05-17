package ru.blimfy.direct.usecase.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.common.enumeration.DmChannelTypes
import ru.blimfy.direct.db.entity.DmChannel

/**
 * Интерфейс для работы с личными диалогами или группами.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface DmChannelService {
    /**
     * Возвращает новый личный канал с [type], [recipients] и [ownerId].
     */
    suspend fun createDmChannel(recipients: Set<UUID>, type: DmChannelTypes, ownerId: UUID? = null): DmChannel

    /**
     * Возвращает группу с [id], [newName] и [newIcon].
     */
    suspend fun modifyGroupDm(id: UUID, newName: String, newIcon: String? = null): DmChannel

    /**
     * Возвращает группу с [id] и [newRecipients].
     */
    suspend fun addRecipientsGroupDm(id: UUID, newRecipients: Set<UUID>): DmChannel

    /**
     * Возвращает группу с [id] и удалённым [recipientId].
     */
    suspend fun deleteRecipientGroupDm(id: UUID, recipientId: UUID): DmChannel

    /**
     * Возвращает группу с [id] и [newOwnerId].
     */
    suspend fun changeOwnerGroupDm(id: UUID, newOwnerId: UUID): DmChannel

    /**
     * Возвращает личный канал с [id].
     */
    suspend fun findDmChannel(id: UUID): DmChannel

    /**
     * Возвращает личные диалоги или группы для [recipientId].
     */
    fun findDmChannels(recipientId: UUID): Flow<DmChannel>

    /**
     * Возвращает личный диалог для [recipients], если такой есть.
     */
    suspend fun findDm(recipients: Set<UUID>): DmChannel?

    /**
     * Проверяет разрешение пользователя с [userId] на просмотр личного канала с [id].
     */
    suspend fun checkDmChannelViewAccess(id: UUID, userId: UUID)

    /**
     * Проверяет разрешение пользователя с [userId] на изменение группы с [id].
     */
    suspend fun checkGroupDmWriteAccess(id: UUID, userId: UUID)
}