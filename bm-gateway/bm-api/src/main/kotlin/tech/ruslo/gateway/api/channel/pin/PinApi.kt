package tech.ruslo.gateway.api.channel.pin

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.gateway.api.channel.pin.handler.PinApiService
import tech.ruslo.security.service.CustomUserDetails

/**
 * REST API контроллер для работы с закреплёнными сообщениями канала.
 *
 * @property service сервис для обработки информации о закреплённых сообщений каналов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "PinApi", description = "REST API контроллер для работы с закреплёнными сообщениями канала")
@RestController
@RequestMapping("/v1/channels/{channelId}/pins")
class PinApi(private val service: PinApiService) {
    @Operation(summary = "Получить страницу с закреплёнными сообщениями канала")
    @GetMapping("/pins")
    suspend fun findPins(
        @PathVariable channelId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findPins(channelId, userDetails.info)

    @Operation(summary = "Закрепить сообщение")
    @PutMapping("/{messageId}")
    suspend fun pinMessage(
        @PathVariable channelId: UUID,
        @PathVariable messageId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.changePinned(channelId, messageId, true, userDetails.info)

    @Operation(summary = "Открепить сообщение")
    @DeleteMapping("/{messageId}")
    suspend fun unpinMessage(
        @PathVariable channelId: UUID,
        @PathVariable messageId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.changePinned(channelId, messageId, false, userDetails.info)
}