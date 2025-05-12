package ru.blimfy.server.db.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию об участнике сервера.
 *
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property nick ник участника сервера.
 * @property createdDate дата присоединения к серверу.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Member(val serverId: UUID, val userId: UUID) : BaseEntity() {
    var nick: String? = null

    @CreatedDate
    lateinit var createdDate: Instant
}