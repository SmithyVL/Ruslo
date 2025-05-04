package ru.blimfy.server.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о приглашении на сервер.
 *
 * @property authorMemberId идентификатор участника сервера, создавшего приглашение.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Invite(val authorMemberId: UUID, val serverId: UUID) : BaseEntity()