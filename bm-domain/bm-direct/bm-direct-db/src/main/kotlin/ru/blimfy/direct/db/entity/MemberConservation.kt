package ru.blimfy.direct.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.direct.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию об участнике личного диалога.
 *
 * @property conservationId идентификатор личного диалога.
 * @property userId идентификатор пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class MemberConservation(val conservationId: UUID, val userId: UUID) : BaseEntity()