package ru.blimfy.gateway.controller.user

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
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.user.friend.request.NewFriendRequestDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.friend.request.FriendRequestControllerService

/**
 * Контроллер для работы с запросами в друзья.
 *
 * @property friendRequestControllerService сервис для обработки информации о запросах в друзья.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "FriendRequestController", description = "Контроллер для работы с информацией о запросах в друзья")
@RestController
@RequestMapping("/v1/friend-requests")
class FriendRequestController(private val friendRequestControllerService: FriendRequestControllerService) {
    @Operation(summary = "Создать запрос в друзья")
    @PostMapping
    suspend fun createFriendRequest(
        @RequestBody newFriendRequest: NewFriendRequestDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendRequestControllerService.createFriendRequest(newFriendRequest, userDetails.info)

    @Operation(summary = "Получить все исходящие запросы в друзья")
    @GetMapping("/outgoing")
    suspend fun findAllOutgoingFriendRequests(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendRequestControllerService.findOutgoingFriendRequests(userDetails.info)

    @Operation(summary = "Получить все входящие запросы в друзья")
    @GetMapping("/incoming")
    suspend fun findAllIncomingFriendRequests(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendRequestControllerService.findIncomingFriendRequests(userDetails.info)

    @Operation(summary = "Удалить все исходящие запросы в друзья")
    @DeleteMapping("/outgoing")
    suspend fun deleteAllOutgoingFriendRequests(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendRequestControllerService.deleteAllOutgoingFriendRequests(userDetails.info)

    @Operation(summary = "Удалить все входящие запросы в друзья")
    @DeleteMapping("/incoming")
    suspend fun deleteAllIncomingFriendRequests(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendRequestControllerService.deleteAllIncomingFriendRequests(userDetails.info)

    @Operation(summary = "Удалить исходящий запрос в друзья")
    @DeleteMapping("/outgoing/{friendRequestId}")
    suspend fun deleteOutgoingFriendRequest(
        @PathVariable friendRequestId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendRequestControllerService.deleteOutgoingFriendRequest(friendRequestId, userDetails.info)

    @Operation(summary = "Удалить входящий запрос в друзья")
    @DeleteMapping("/incoming/{friendRequestId}")
    suspend fun deleteIncomingFriendRequest(
        @PathVariable friendRequestId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendRequestControllerService.deleteIncomingFriendRequest(friendRequestId, userDetails.info)

    @Operation(summary = "Принять запрос в друзья")
    @PutMapping("/incoming/{friendRequestId}")
    suspend fun acceptFriendRequest(
        @PathVariable friendRequestId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendRequestControllerService.acceptFriendRequest(friendRequestId, userDetails.info)
}