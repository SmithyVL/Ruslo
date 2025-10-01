package tech.ruslo.channel.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.channel.dto.message.MessageClient
import tech.ruslo.channel.dto.message.MessageDto
import tech.ruslo.channel.mapper.toDto
import tech.ruslo.channel.mapper.toEntity
import tech.ruslo.channel.service.message.MessageService

/**
 * REST API контроллер для работы с сообщениями.
 *
 * @property messageService сервис для обработки информации о сообщениях.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "MessageController", description = "REST API контроллер для работы с сообщениями")
@RestController
@RequestMapping("/messages")
class MessageController(private val messageService: MessageService) : MessageClient {
    @Operation(summary = "Сохранить сообщение")
    @PostMapping
    override suspend fun saveMessage(@RequestBody messageDto: MessageDto) =
        messageService.saveMessage(messageDto.toEntity()).toDto()

    @Operation(summary = "Получить страницу с сообщениями")
    @GetMapping
    override fun findMessages(
        @RequestParam channelId: Long,
        @RequestParam around: Long?,
        @RequestParam before: Long?,
        @RequestParam after: Long?,
        @RequestParam limit: Int,
    ) = messageService.findMessages(channelId, around, before, after, limit).map { it.toDto() }

    @Operation(summary = "Удалить сообщение")
    @DeleteMapping("/{id}")
    override suspend fun deleteMessage(@PathVariable id: Long) = messageService.deleteMessage(id)
}