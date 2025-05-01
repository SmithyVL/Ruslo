package ru.blimfy.direct.db.entity

import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.direct.db.entity.base.BaseEntity

/**
 * Сущность с информацией о личном диалоге.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class Conservation : BaseEntity()