package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.ServerDto
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Сущность, которая хранит в себе информацию о сервере.
 *
 * @property ownerId идентификатор пользователя, владеющего сервером.
 * @property name название.
 * @property avatarUrl ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Server(val ownerId: UUID, val name: String) : WithBaseData {
    var avatarUrl: String? = null

    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}

/**
 * Возвращает DTO представление сущности сервера.
 */
fun Server.toDto() = ServerDto(id, ownerId, name, avatarUrl, createdDate, updatedDate)

/**
 * Возвращает сущность сервера из DTO.
 */
fun ServerDto.toEntity() = Server(ownerId, name).apply {
    this@toEntity.id?.let { id = it }
    this@toEntity.avatarUrl?.let { avatarUrl = it }
    this@toEntity.createdDate?.let { createdDate = it }
    this@toEntity.updatedDate?.let { updatedDate = it }
}