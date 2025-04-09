package ru.blimfy.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.ChannelDto
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.services.channel.ChannelService
import ru.blimfy.services.message.TextMessageService

/**
 * REST API контроллер для работы с информацией о каналах.
 *
 * @property channelService сервис для работы с каналами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ChannelController", description = "REST API для работы с каналами серверов")
@RestController
@RequestMapping("/v1/channels")
class ChannelController(private val channelService: ChannelService, private val messageService: TextMessageService) {
    @Operation(summary = "Создать/Обновить канал сервера")
    @PostMapping
    suspend fun saveChannel(@RequestBody channelDto: ChannelDto) =
        channelService.saveChannel(channelDto.toEntity()).toDto()

    @Operation(summary = "Получить канал по его идентификатору")
    @GetMapping("/{channelId}")
    suspend fun findChannel(@PathVariable channelId: UUID) = channelService.findChannel(channelId).toDto()

    @Operation(summary = "Удалить канал по его идентификатору")
    @DeleteMapping("/{channelId}")
    suspend fun deleteChannel(@PathVariable channelId: UUID) = channelService.deleteChannel(channelId)

    @Operation(summary = "Получить все сообщения канала")
    @GetMapping("/{channelId}/messages")
    suspend fun findChannelMessages(@PathVariable channelId: UUID) =
        messageService.findChannelMessages(channelId).map { it.toDto() }
}