package ru.blimfy.gateway.service.dm

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.common.enumeration.DmChannelTypes.DM
import ru.blimfy.direct.db.entity.DmChannel
import ru.blimfy.direct.usecase.channel.DmChannelService
import ru.blimfy.direct.usecase.message.DmMessageService
import ru.blimfy.gateway.dto.dm.channel.DmChannelDto
import ru.blimfy.gateway.dto.dm.channel.GroupDmOwnerDto
import ru.blimfy.gateway.dto.dm.channel.GroupDmRecipientsDto
import ru.blimfy.gateway.dto.dm.channel.ModifyGroupDmDto
import ru.blimfy.gateway.dto.dm.channel.NewDmChannelDto
import ru.blimfy.gateway.dto.dm.channel.toDto
import ru.blimfy.gateway.dto.dm.message.DmMessageDto
import ru.blimfy.gateway.dto.dm.message.toDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService

/**
 * Реализация интерфейса для работы с обработкой запросов о личных каналах.
 *
 * @property dmChannelService сервис для работы с личными каналами.
 * @property dmMessageService сервис для работы с личными сообщениями.
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class DmChannelControllerServiceImpl(
    private val dmChannelService: DmChannelService,
    private val dmMessageService: DmMessageService,
    private val userService: UserService,
) : DmChannelControllerService {
    override suspend fun createDmChannel(newDmChannel: NewDmChannelDto, currentUser: User): DmChannelDto {
        // Формируем итоговый список участников нового личного диалога или группы вместе с текущим пользователем.
        val recipientsWithCurrentUser = newDmChannel.recipients + currentUser.id

        // Если пользователь создает личный диалог, то сначала ищем нет ли такого диалога и возвращаем его, а иначе
        // формируем поле "ownerId", которое является пустым для личных диалогов.
        val ownerId = if (newDmChannel.type == DM) {
            val existedDm = dmChannelService.findDm(recipientsWithCurrentUser)
            if (existedDm != null) {
                return existedDm.toDtoWithRecipientUsers()
            }

            null
        } else {
            currentUser.id
        }

        return dmChannelService.createDmChannel(recipientsWithCurrentUser, newDmChannel.type, ownerId)
            .toDtoWithRecipientUsers()
    }

    override suspend fun modifyGroupDm(modifyGroupDm: ModifyGroupDmDto, currentUser: User): DmChannelDto {
        val dmChannelId = modifyGroupDm.dmChannelId

        // Изменять группу может только его владелец.
        dmChannelService.checkGroupDmWriteAccess(id = dmChannelId, userId = currentUser.id)

        return dmChannelService.modifyGroupDm(dmChannelId, modifyGroupDm.name, modifyGroupDm.icon)
            .toDtoWithRecipientUsers()
    }

    override suspend fun changeGroupDmOwner(
        dmChannelId: UUID,
        groupDmOwner: GroupDmOwnerDto,
        currentUser: User,
    ): DmChannelDto {
        // Изменять группу может только его владелец.
        dmChannelService.checkGroupDmWriteAccess(id = dmChannelId, userId = currentUser.id)

        return dmChannelService.changeOwnerGroupDm(dmChannelId, groupDmOwner.ownerId)
            .toDtoWithRecipientUsers()
    }

    override suspend fun addGroupDmRecipients(
        dmChannelId: UUID,
        groupDmRecipients: GroupDmRecipientsDto,
        currentUser: User,
    ): DmChannelDto {
        // Изменять группу может только его владелец.
        dmChannelService.checkGroupDmWriteAccess(id = dmChannelId, userId = currentUser.id)

        return dmChannelService.addRecipientsGroupDm(dmChannelId, groupDmRecipients.recipients)
            .toDtoWithRecipientUsers()
    }

    override suspend fun removeGroupDmRecipient(
        dmChannelId: UUID,
        recipientId: UUID,
        currentUser: User,
    ): DmChannelDto {
        // Изменять группу может только его владелец.
        dmChannelService.checkGroupDmWriteAccess(id = dmChannelId, userId = currentUser.id)

        return dmChannelService.deleteRecipientGroupDm(dmChannelId, recipientId)
            .toDtoWithRecipientUsers()
    }

    override suspend fun findDmChannelMessages(
        dmChannelId: UUID,
        pageNumber: Int,
        pageSize: Int,
        currentUser: User,
    ): Flow<DmMessageDto> {
        // Получить сообщения личного канала может только его участник.
        dmChannelService.checkDmChannelViewAccess(id = dmChannelId, userId = currentUser.id)

        return dmMessageService.findMessages(dmChannelId, pageNumber, pageSize)
            .map { it.toDto().apply { author = userService.findUser(it.authorId).toDto() } }
    }

    override suspend fun findDmChannelPins(
        dmChannelId: UUID,
        pageNumber: Int,
        pageSize: Int,
        currentUser: User
    ): Flow<DmMessageDto> {
        // Получить сообщения личного канала может только его участник.
        dmChannelService.checkDmChannelViewAccess(id = dmChannelId, userId = currentUser.id)

        return dmMessageService.findPinnedMessages(dmChannelId, pageNumber, pageSize)
            .map { it.toDto().apply { author = userService.findUser(it.authorId).toDto() } }
    }

    /**
     * Возвращает DTO представление личного диалога или группы с информацией о пользователях.
     */
    private suspend fun DmChannel.toDtoWithRecipientUsers() =
        this.toDto().apply {
            recipients = this@toDtoWithRecipientUsers.recipients
                .map { recipientId -> userService.findUser(recipientId) }
                .map(User::toDto)
        }
}