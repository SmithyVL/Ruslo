package ru.blimfy.gateway.controller

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
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.NewChannelDto
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
        @RequestBody newChannelDto: NewChannelDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = channelControllerService.createChannel(newChannelDto, user)

    @Operation(summary = "Обновить канал сервера")
    @PutMapping
    suspend fun modifyChannel(
        @RequestBody channelDto: ChannelDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = channelControllerService.modifyChannel(channelDto, user)

    @Operation(summary = "Удалить канал по его идентификатору")
    @DeleteMapping("/{channelId}")
    suspend fun deleteChannel(
        @PathVariable channelId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = channelControllerService.deleteChannel(channelId, user)

    @Operation(summary = "Получить канал по его идентификатору")
    @GetMapping("/{channelId}")
    suspend fun findChannel(
        @PathVariable channelId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = channelControllerService.findChannel(channelId, user)

    @Operation(summary = "Получить страницу с сообщениями канала")
    @GetMapping("/{channelId}/messages")
    suspend fun findChannelMessages(
        @PathVariable channelId: UUID,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = channelControllerService.findChannelMessages(channelId, pageNumber, pageSize, user)
}