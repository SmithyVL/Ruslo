package ru.blimfy.gateway.api.channel

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.domain.channel.api.dto.channel.ModifyChannelDto
import ru.blimfy.gateway.api.channel.handler.ChannelApiService
import ru.blimfy.security.service.CustomUserDetails

/**
 * REST API контроллер для работы с каналами.
 *
 * @property service сервис для обработки информации о каналах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ChannelApi", description = "REST API контроллер для работы с каналами")
@RestController
@RequestMapping("/v1/channels")
class ChannelApi(private val service: ChannelApiService) {
    @Operation(summary = "Изменить канал")
    @PatchMapping("/{id}")
    suspend fun modifyChannel(
        @PathVariable id: UUID,
        @RequestBody modifyChannel: ModifyChannelDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.modifyChannel(id, modifyChannel, userDetails.info)

    @Operation(summary = "Удалить канал")
    @DeleteMapping("/{id}")
    suspend fun deleteChannel(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteChannel(id, userDetails.info)

    @Operation(summary = "Получить приглашения канала")
    @GetMapping("/{id}/invites")
    suspend fun findInvites(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findInvites(id, userDetails.info)

    @Operation(summary = "Запустить индикатор ввода текста для канала")
    @PostMapping("/{id}/typing")
    suspend fun triggerTypingIndicator(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.triggerTypingIndicator(id, userDetails.info)
}