package ru.blimfy.gateway.controller.server

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.server.channel.ModifyChannelDto
import ru.blimfy.gateway.dto.server.channel.NewChannelDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.channel.ChannelControllerService

/**
 * Контроллер для работы с информацией о каналах.
 *
 * @property channelControllerService сервис для обработки информации о каналах серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ChannelController", description = "Контроллер для работы с каналами серверов")
@RestController
@RequestMapping("/v1/channels")
class ChannelController(private val channelControllerService: ChannelControllerService) {
    @Operation(summary = "Создать канал сервера")
    @PostMapping
    suspend fun createChannel(
        @RequestBody newChannel: NewChannelDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = channelControllerService.createChannel(newChannel, userDetails.info)

    @Operation(summary = "Обновить канал сервера")
    @PutMapping
    suspend fun modifyChannel(
        @RequestBody modifyChannel: ModifyChannelDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = channelControllerService.modifyChannel(modifyChannel, userDetails.info)

    @Operation(summary = "Удалить канал по его идентификатору")
    @DeleteMapping("/{channelId}")
    suspend fun deleteChannel(
        @PathVariable channelId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = channelControllerService.deleteChannel(channelId, userDetails.info)

    @Operation(summary = "Получить канал по его идентификатору")
    @GetMapping("/{channelId}")
    suspend fun findChannel(
        @PathVariable channelId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = channelControllerService.findChannel(channelId, userDetails.info)

    @Operation(summary = "Получить страницу с сообщениями канала")
    @GetMapping("/{channelId}/messages")
    suspend fun findChannelMessages(
        @PathVariable channelId: UUID,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) =
        channelControllerService.findChannelMessages(channelId, pageNumber, pageSize, userDetails.info)
}