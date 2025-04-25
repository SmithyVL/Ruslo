package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.server.NewServerDto
import ru.blimfy.common.dto.server.ServerDto
import ru.blimfy.persistence.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о сервере.
 *
 * @property ownerId идентификатор пользователя, владеющего сервером.
 * @property name название.
 * @property avatarUrl ссылка на файл аватарки.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Server(val ownerId: UUID, val name: String) : BaseEntity() {
    var avatarUrl: String? = null

    @CreatedDate
    lateinit var createdDate: Instant
}

/**
 * Возвращает DTO представление сущности сервера.
 */
fun Server.toDto() = ServerDto(id, ownerId, name, createdDate)
    .apply { this.avatarUrl = this@toDto.avatarUrl }

/**
 * Возвращает сущность сервера из DTO с новым сервером.
 */
fun NewServerDto.toEntity(ownerId: UUID) = Server(ownerId, name).apply {
    avatarUrl = this@toEntity.avatarUrl
}

/**
 * Возвращает сущность сервера из DTO.
 */
fun ServerDto.toEntity(ownerId: UUID) = Server(ownerId, name).apply {
    id = this@toEntity.id
    createdDate = this@toEntity.createdDate
    avatarUrl = this@toEntity.avatarUrl
}