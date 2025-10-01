package tech.ruslo.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.user.dto.UserClient
import tech.ruslo.user.dto.UserDto
import tech.ruslo.user.mapper.toDto
import tech.ruslo.user.mapper.toEntity
import tech.ruslo.user.service.UserService

/**
 * REST API контроллер для работы с пользователями.
 *
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "UserController", description = "REST API контроллер для работы с пользователями")
@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) : UserClient {
    @Operation(summary = "Сохранить пользователя")
    @PostMapping
    override suspend fun saveUser(@RequestBody userDto: UserDto) =
        userService.saveUser(userDto.toEntity()).toDto()

    @Operation(summary = "Получить пользователя по имени")
    @GetMapping
    override suspend fun getUser(@RequestParam username: String) =
        userService.findUser(username).toDto()

    @Operation(summary = "Получить пользователя по идентификатору")
    @GetMapping("/{id}")
    override suspend fun getUser(@PathVariable id: Long) =
        userService.findUser(id).toDto()
}