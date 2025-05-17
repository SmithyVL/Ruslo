package ru.blimfy.user.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.user.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о заявке в друзья.
 *
 * @property fromId идентификатор пользователя, приглашающего в друзья.
 * @property toId идентификатор пользователя, получившего приглашение.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class FriendRequest(val fromId: UUID, val toId: UUID) : BaseEntity()