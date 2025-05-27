package ru.blimfy.gateway.api.user.request

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.api.user.request.handler.FriendRequestApiService
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * REST API контроллер для работы с запросами в друзья.
 *
 * @property service сервис для обработки информации о запросах в друзья.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "FriendRequestApi", description = "REST API контроллер для работы с информацией о запросах в друзья")
@RestController
@RequestMapping("/v1/users/@me/requests")
class FriendRequestApi(private val service: FriendRequestApiService) {
    @Operation(summary = "Создать запрос в друзья с пользователем")
    @PostMapping("/{username}")
    suspend fun createFriendRequest(
        @PathVariable username: String,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createFriendRequest(username, userDetails.info)

    @Operation(summary = "Получить исходящие запросы в друзья")
    @GetMapping("/outgoing")
    suspend fun findAllOutgoingFriendRequests(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findOutgoingFriendRequests(userDetails.info)

    @Operation(summary = "Удалить исходящие запросы в друзья")
    @DeleteMapping("/outgoing")
    suspend fun deleteAllOutgoingFriendRequests(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteAllOutgoingFriendRequests(userDetails.info)

    @Operation(summary = "Удалить исходящий запрос в друзья с пользователем")
    @DeleteMapping("/outgoing/{userId}")
    suspend fun deleteOutgoingFriendRequest(
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteOutgoingFriendRequest(userId, userDetails.info)

    @Operation(summary = "Получить входящие запросы в друзья")
    @GetMapping("/incoming")
    suspend fun findAllIncomingFriendRequests(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findIncomingFriendRequests(userDetails.info)

    @Operation(summary = "Удалить входящие запросы в друзья")
    @DeleteMapping("/incoming")
    suspend fun deleteAllIncomingFriendRequests(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteAllIncomingFriendRequests(userDetails.info)

    @Operation(summary = "Удалить входящий запрос в друзья от пользователя")
    @DeleteMapping("/incoming/{userId}")
    suspend fun deleteIncomingFriendRequest(
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteIncomingFriendRequest(userId, userDetails.info)

    @Operation(summary = "Принять запрос в друзья от пользователя")
    @PutMapping("/incoming/{userId}")
    suspend fun acceptFriendRequest(
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.acceptFriendRequest(userId, userDetails.info)
}