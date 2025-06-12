package ru.blimfy.gateway.api.mapper

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import ru.blimfy.gateway.api.dto.RoleDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.server.dto.NewServerDto
import ru.blimfy.gateway.api.server.dto.ServerDto
import ru.blimfy.server.db.entity.Role
import ru.blimfy.server.db.entity.Server
import ru.blimfy.server.usecase.role.RoleService
import java.util.*

/**
 * Маппер для превращения сервера в DTO и обратно.
 *
 * @property roleService сервис для работы с ролями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class ServerMapper(private val roleService: RoleService) {
    /**
     * Возвращает сущность сервера из [dto] для пользователя с [userId], который является владельцем сервера.
     */
    fun toEntity(dto: NewServerDto, userId: UUID) =
        Server(userId, dto.name).apply { icon = dto.icon }

    /**
     * Возвращает DTO представление [server] с расширенной информацией для пользователя с [userId], который является
     * участником сервера.
     */
    suspend fun toDto(server: Server, userId: UUID) =
        server.toBasicDto().apply {
            owner = ownerId == userId
            roles = roleService.findServerRoles(id).map(Role::toDto).toList()
            permissions = roles
                .map(RoleDto::permissions)
                .map { it.toLong() }
                .sumOf { it }
                .toString()
        }

    /**
     * Возвращает DTO представление сервера с базовой информацией.
     */
    private fun Server.toBasicDto() =
        ServerDto(id, name, ownerId, icon, bannerColor, description, createdDate)
}