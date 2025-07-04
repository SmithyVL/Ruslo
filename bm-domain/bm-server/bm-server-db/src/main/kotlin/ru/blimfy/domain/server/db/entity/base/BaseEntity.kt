package ru.blimfy.domain.server.db.entity.base

import java.util.UUID
import org.springframework.data.annotation.Id

/**
 * Абстрактный класс с базовой информацией о сущности.
 *
 * @property id идентификатор сущности.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
abstract class BaseEntity {
    @Id
    lateinit var id: UUID
}