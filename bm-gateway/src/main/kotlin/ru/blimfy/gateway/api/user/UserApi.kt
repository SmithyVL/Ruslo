package ru.blimfy.gateway.api.user

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
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.UsernameDto
import ru.blimfy.gateway.dto.user.channel.NewDmChannelDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.user.UserApiService

/**
 * REST API контроллер для работы с пользователями.
 *
 * @property service сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "UserApi", description = "REST API контроллер для работы с информацией пользователей")
@RestController
@RequestMapping("/v1/users/@me")
class UserApi(private val service: UserApiService) {
    @Operation(summary = "Получить информацию о текущем пользователе")
    @GetMapping
    suspend fun getCurrentUser(@AuthenticationPrincipal userDetails: CustomUserDetails) =
        userDetails.info.toDto()

    @Operation(summary = "Обновить информацию о текущем пользователе")
    @PatchMapping
    suspend fun modifyUser(
        @RequestBody modifyUser: ModifyUserDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.modifyUser(modifyUser, userDetails.info)

    @Operation(summary = "Обновить имя текущего пользователя")
    @PutMapping("/username")
    suspend fun changeUsername(
        @RequestBody usernameInfo: UsernameDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.changeUsername(usernameInfo, userDetails.info)

    @Operation(summary = "Получить краткую информацию о серверах текущего пользователя")
    @GetMapping("/servers")
    suspend fun findServers(@AuthenticationPrincipal userDetails: CustomUserDetails) =
        service.findUserServers(userDetails.info)

    @Operation(summary = "Получить информацию участника сервера для текущего пользователя")
    @GetMapping("/servers/{serverId}/member")
    suspend fun findMember(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findMember(serverId, userDetails.info)

    @Operation(summary = "Покинуть сервер пользователем")
    @DeleteMapping("/servers/{serverId}")
    suspend fun leaveServer(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.leaveServer(serverId, userDetails.info)

    @Operation(summary = "Получить личные каналы пользователя")
    @GetMapping("/channels")
    suspend fun findUserChannels(@AuthenticationPrincipal userDetails: CustomUserDetails) =
        service.findUserDmChannels(userDetails.info)

    @Operation(summary = "Создать личный канал")
    @PostMapping("/channels")
    suspend fun createDmChannel(
        @RequestBody channel: NewDmChannelDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createDmChannel(channel, userDetails.info)
}