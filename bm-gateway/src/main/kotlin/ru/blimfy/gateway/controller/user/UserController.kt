package ru.blimfy.gateway.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.UsernameDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.user.UserControllerService

/**
 * Контроллер для работы с пользователями.
 *
 * @property userControllerService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "UserController", description = "Контроллер для работы с информацией пользователей")
@RestController
@RequestMapping("/v1/users/@me")
class UserController(private val userControllerService: UserControllerService) {
    @Operation(summary = "Получить информацию о текущем пользователе")
    @GetMapping
    suspend fun getCurrentUser(@AuthenticationPrincipal userDetails: CustomUserDetails) =
        userDetails.info.toDto()

    @Operation(summary = "Обновить информацию о текущем пользователе")
    @PutMapping
    suspend fun modifyUser(
        @RequestBody modifyUser: ModifyUserDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = userControllerService.modifyUser(modifyUser, userDetails.info)

    @Operation(summary = "Обновить имя текущего пользователя")
    @PatchMapping
    suspend fun changeUsername(
        @RequestBody usernameInfo: UsernameDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = userControllerService.changeUsername(usernameInfo, userDetails.info)

    @Operation(summary = "Получить краткую информацию о серверах текущего пользователя")
    @GetMapping("/servers")
    suspend fun findUserServers(@AuthenticationPrincipal userDetails: CustomUserDetails) =
        userControllerService.findUserServers(userDetails.info)

    @Operation(summary = "Получить информацию об участии на сервере для текущего пользователя")
    @GetMapping("/servers/{serverId}/member")
    suspend fun findServerMember(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = userControllerService.findServerMember(serverId, userDetails.info)

    @Operation(summary = "Покинуть сервер пользователем")
    @DeleteMapping("/servers/{serverId}")
    suspend fun leaveServer(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = userControllerService.leaveServer(serverId, userDetails.info)

    @Operation(summary = "Получить все личные каналы пользователя")
    @GetMapping("/dm-channels")
    suspend fun findUserDmChannels(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = userControllerService.findUserDmChannels(userDetails.info)

    @Operation(summary = "Получить всех друзей пользователя")
    @GetMapping("/friends")
    suspend fun findUserFriends(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = userControllerService.findUserFriends(userDetails.info)
}