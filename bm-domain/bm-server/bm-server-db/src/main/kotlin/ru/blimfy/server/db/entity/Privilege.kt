package ru.blimfy.server.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.enumeration.PrivilegeTypes
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о привилегии.
 *
 * @property roleId идентификатор роли.
 * @property type тип привилегии.
 * @property isGranted флаг того, что привилегия является разрешающей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Privilege(val roleId: UUID, val type: PrivilegeTypes, val isGranted: Boolean = false) : BaseEntity()