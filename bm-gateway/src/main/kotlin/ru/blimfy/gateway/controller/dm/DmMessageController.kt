package ru.blimfy.gateway.controller.dm

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.dm.message.ModifyDmMessageDto
import ru.blimfy.gateway.dto.dm.message.NewDmMessageDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.dm.message.DmMessageControllerService

/**
 * Контроллер для работы с личными сообщениями.
 *
 * @property dmMessageControllerService сервис для обработки информации о личных сообщениях.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "DmMessageController", description = "Контроллер для работы с личными сообщениями пользователей")
@RestController
@RequestMapping("/v1/dm-messages")
class DmMessageController(private val dmMessageControllerService: DmMessageControllerService) {
    @Operation(summary = "Создать личное сообщение")
    @PostMapping
    suspend fun createDmMessage(
        @RequestBody newDmMessage: NewDmMessageDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmMessageControllerService.createDmMessage(newDmMessage, userDetails.info)

    @Operation(summary = "Обновить личное сообщение")
    @PatchMapping("/{dmMessageId}")
    suspend fun modifyDmMessage(
        @PathVariable dmMessageId: UUID,
        @RequestBody modifyDmMessage: ModifyDmMessageDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmMessageControllerService.setDmMessageContent(dmMessageId, modifyDmMessage, userDetails.info)

    @Operation(summary = "Удалить личное сообщение")
    @DeleteMapping("/{dmMessageId}")
    suspend fun deleteDmMessage(
        @PathVariable dmMessageId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmMessageControllerService.deleteDmMessage(dmMessageId, userDetails.info)

    @Operation(summary = "Закрепить личное сообщение")
    @PutMapping("/{dmMessageId}/pins")
    suspend fun pinDmMessage(
        @PathVariable dmMessageId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmMessageControllerService.setDmMessagePinned(dmMessageId, true, userDetails.info)

    @Operation(summary = "Открепить личное сообщение")
    @DeleteMapping("/{dmMessageId}/pins")
    suspend fun unpinDmMessage(
        @PathVariable dmMessageId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = dmMessageControllerService.setDmMessagePinned(dmMessageId, false, userDetails.info)
}