package ru.blimfy.gateway.service.dm

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.dm.channel.DmChannelDto
import ru.blimfy.gateway.dto.dm.channel.GroupDmOwnerDto
import ru.blimfy.gateway.dto.dm.channel.GroupDmRecipientsDto
import ru.blimfy.gateway.dto.dm.channel.ModifyGroupDmDto
import ru.blimfy.gateway.dto.dm.channel.NewDmChannelDto
import ru.blimfy.gateway.dto.dm.message.DmMessageDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о личных диалогах или группах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface DmChannelControllerService {
    /**
     * Возвращает [newDmChannel] для [currentUser].
     */
    suspend fun createDmChannel(newDmChannel: NewDmChannelDto, currentUser: User): DmChannelDto

    /**
     * Возвращает обновлённую [modifyGroupDm] для [currentUser].
     */
    suspend fun modifyGroupDm(modifyGroupDm: ModifyGroupDmDto, currentUser: User): DmChannelDto

    /**
     * [currentUser] передаёт владение групповым личным диалогом с [dmChannelId] новому [groupDmOwner].
     */
    suspend fun changeGroupDmOwner(dmChannelId: UUID, groupDmOwner: GroupDmOwnerDto, currentUser: User): DmChannelDto

    /**
     * [currentUser] добавляет новых [groupDmRecipients] в групповой личный диалог с [dmChannelId].
     */
    suspend fun addGroupDmRecipients(
        dmChannelId: UUID,
        groupDmRecipients: GroupDmRecipientsDto,
        currentUser: User,
    ): DmChannelDto

    /**
     * [currentUser] удаляет пользователя с [recipientId] из группового личного диалога с [dmChannelId].
     */
    suspend fun removeGroupDmRecipient(dmChannelId: UUID, recipientId: UUID, currentUser: User): DmChannelDto

    /**
     * Возвращает [pageNumber] страницу с [pageSize] сообщениями личного диалога с [dmChannelId], которые хочет
     * получить [currentUser].
     */
    suspend fun findDmChannelMessages(
        dmChannelId: UUID,
        pageNumber: Int,
        pageSize: Int,
        currentUser: User,
    ): Flow<DmMessageDto>

    /**
     * Возвращает [pageNumber] страницу с [pageSize] закреплённых сообщений личного канала с [dmChannelId], которые
     * хочет получить [currentUser].
     */
    suspend fun findDmChannelPins(
        dmChannelId: UUID,
        pageNumber: Int,
        pageSize: Int,
        currentUser: User,
    ): Flow<DmMessageDto>
}