package ru.blimfy.persistence.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.persistence.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о роли участника сервера.
 *
 * @property memberId идентификатор участника (не пользователя) сервера.
 * @property roleId идентификатор роли.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class MemberRole(val memberId: UUID, val roleId: UUID) : BaseEntity()