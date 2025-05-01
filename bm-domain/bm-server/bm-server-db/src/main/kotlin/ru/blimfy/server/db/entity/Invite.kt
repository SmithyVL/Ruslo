package ru.blimfy.server.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о приглашении на сервер.
 *
 * @property authorId идентификатор автора приглашения.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Invite(val authorId: UUID, val serverId: UUID) : BaseEntity()