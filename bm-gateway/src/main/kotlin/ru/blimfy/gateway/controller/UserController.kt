package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.user.ModifyUserDto
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
    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping
    suspend fun getCurrentUser(@AuthenticationPrincipal user: CustomUserDetails) =
        user.userInfo.toDto()

    @Operation(summary = "Обновить информацию о пользователе")
    @PutMapping
    suspend fun modifyUser(
        @RequestBody modifyUser: ModifyUserDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = userControllerService.modifyUser(modifyUser, user)

    @Operation(summary = "Получить все сервера пользователя")
    @GetMapping("/servers")
    suspend fun findUserServers(@AuthenticationPrincipal user: CustomUserDetails) =
        userControllerService.findUserServers(user)

    @Operation(summary = "Получить все личные диалоги пользователя")
    @GetMapping("/conservations")
    suspend fun findUserConservations(@AuthenticationPrincipal user: CustomUserDetails) =
        userControllerService.findUserConservations(user)

    @Operation(summary = "Покинуть сервер пользователем")
    @DeleteMapping("/servers/{serverId}")
    suspend fun leaveServer(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = userControllerService.leaveServer(serverId, user)
}