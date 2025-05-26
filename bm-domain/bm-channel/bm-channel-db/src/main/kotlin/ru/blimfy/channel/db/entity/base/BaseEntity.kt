package ru.blimfy.channel.db.entity.base

import java.util.UUID
import org.springframework.data.annotation.Id

/**
 * Абстрактный класс с базовой информацией о сущности в базе данных.
 *
 * @property id идентификатор сущности.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
abstract class BaseEntity {
    @Id
    lateinit var id: UUID
}