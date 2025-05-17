package ru.blimfy.gateway.controller.dm

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.dm.channel.GroupDmOwnerDto
import ru.blimfy.gateway.dto.dm.channel.GroupDmRecipientsDto
import ru.blimfy.gateway.dto.dm.channel.ModifyGroupDmDto
import ru.blimfy.gateway.dto.dm.channel.NewDmChannelDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.dm.DmChannelControllerService

/**
 * Контроллер для работы с личными каналами.
 *
 * @property dmChannelControllerService сервис для обработки информации о личных каналах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "DmChannelController", description = "Контроллер для работы с личными каналами")
@RestController
@RequestMapping("/v1/dm-channels")
class DmChannelController(private val dmChannelControllerService: DmChannelControllerService) {
    @Operation(summary = "Создать личный канал")
    @PostMapping
    suspend fun createDmChannel(
        @RequestBody newDmChannel: NewDmChannelDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmChannelControllerService.createDmChannel(newDmChannel, userDetails.info)

    @Operation(summary = "Изменить группу")
    @PutMapping
    suspend fun modifyGroupDm(
        @RequestBody modifyGroupDm: ModifyGroupDmDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmChannelControllerService.modifyGroupDm(modifyGroupDm, userDetails.info)

    @Operation(summary = "Изменить владельца группы")
    @PatchMapping("/{dmChannelId}")
    suspend fun changeOwner(
        @PathVariable dmChannelId: UUID,
        @RequestBody groupDmOwner: GroupDmOwnerDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmChannelControllerService.changeGroupDmOwner(dmChannelId, groupDmOwner, userDetails.info)

    @Operation(summary = "Добавить участников в группу")
    @PatchMapping("/{dmChannelId}/recipients")
    suspend fun addRecipients(
        @PathVariable dmChannelId: UUID,
        @RequestBody groupDmRecipients: GroupDmRecipientsDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmChannelControllerService.addGroupDmRecipients(dmChannelId, groupDmRecipients, userDetails.info)

    @Operation(summary = "Удалить участника из группы")
    @DeleteMapping("/{dmChannelId}/recipients/{recipientId}")
    suspend fun removeRecipient(
        @PathVariable dmChannelId: UUID,
        @PathVariable recipientId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmChannelControllerService.removeGroupDmRecipient(dmChannelId, recipientId, userDetails.info)

    @Operation(summary = "Получить страницу с сообщениями личного канала")
    @GetMapping("/{dmChannelId}/messages")
    suspend fun findDmChannelMessages(
        @PathVariable dmChannelId: UUID,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) =
        dmChannelControllerService.findDmChannelMessages(dmChannelId, pageNumber, pageSize, userDetails.info)

    @Operation(summary = "Получить страницу с закреплёнными сообщениями личного канала")
    @GetMapping("/{dmChannelId}/pins")
    suspend fun findDmChannelPins(
        @PathVariable dmChannelId: UUID,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) =
        dmChannelControllerService.findDmChannelPins(dmChannelId, pageNumber, pageSize, userDetails.info)
}