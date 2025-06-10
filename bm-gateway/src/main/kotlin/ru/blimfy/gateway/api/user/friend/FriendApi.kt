package ru.blimfy.gateway.api.user.friend

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
import ru.blimfy.gateway.api.dto.NewNickDto
import ru.blimfy.gateway.api.user.friend.handler.FriendApiService
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * REST API контроллер для работы с друзьями.
 *
 * @property service сервис для обработки информации о друзьях.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "FriendApi", description = "REST API контроллер для работы с информацией о друзьях")
@RestController
@RequestMapping("/v1/users/@me/friends")
class FriendApi(private val service: FriendApiService) {
    @Operation(summary = "Получить друзей пользователя")
    @GetMapping
    suspend fun findUserFriends(@AuthenticationPrincipal userDetails: CustomUserDetails) =
        service.findUserFriends(userDetails.info)

    @Operation(summary = "Обновить ник для друга")
    @PutMapping("/{userId}/nick")
    suspend fun changeFriendNick(
        @PathVariable userId: UUID,
        @RequestBody nickInfo: NewNickDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.changeFriendNick(userId, nickInfo.nick, userDetails.info)

    @Operation(summary = "Удалить дружбу с пользователем")
    @DeleteMapping("/{userId}")
    suspend fun deleteFriend(
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ) = service.deleteFriend(userId, userDetails.info)
}