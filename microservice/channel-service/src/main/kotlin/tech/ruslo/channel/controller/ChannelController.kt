package tech.ruslo.channel.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.channel.dto.ChannelClient
import tech.ruslo.channel.dto.ChannelDto
import tech.ruslo.channel.mapper.toDto
import tech.ruslo.channel.mapper.toEntity
import tech.ruslo.channel.service.channel.ChannelService

/**
 * REST API контроллер для работы с каналами.
 *
 * @property channelService сервис для обработки информации о каналах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ChannelController", description = "REST API контроллер для работы с каналами")
@RestController
@RequestMapping("/channels")
class ChannelController(private val channelService: ChannelService) : ChannelClient {
    @Operation(summary = "Сохранить канал")
    @PostMapping
    override suspend fun saveChannel(@RequestBody channelDto: ChannelDto) =
        channelService.saveChannel(channelDto.toEntity()).toDto()

    @Operation(summary = "Сохранить каналы")
    @PostMapping("/batch")
    override fun saveChannels(@RequestBody channelDtos: List<ChannelDto>) =
        channelService.saveChannels(channelDtos.map(ChannelDto::toEntity)).map { it.toDto() }

    @Operation(summary = "Удалить канал")
    @DeleteMapping("/{id}")
    override suspend fun deleteChannel(@PathVariable id: Long) = channelService.deleteChannel(id)
}