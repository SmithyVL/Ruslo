package ru.blimfy.gateway.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.user.friend.FriendNickDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.friend.FriendControllerService

/**
 * Контроллер для работы с друзьями.
 *
 * @property friendControllerService сервис для обработки информации о друзьях.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "FriendController", description = "Контроллер для работы с информацией о друзьях")
@RestController
@RequestMapping("/v1/friends")
class FriendController(private val friendControllerService: FriendControllerService) {
    @Operation(summary = "Обновить друга")
    @PutMapping("/{friendId}")
    suspend fun modifyFriend(
        @PathVariable friendId: UUID,
        @RequestBody newFriendRequest: FriendNickDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = friendControllerService.changeFriendNick(friendId, newFriendRequest, userDetails.info)

    @Operation(summary = "Удалить друга")
    @DeleteMapping("/{friendId}")
    suspend fun deleteFriend(@PathVariable friendId: UUID) = friendControllerService.deleteFriend(friendId)
}