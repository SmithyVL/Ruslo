package ru.blimfy.persistence.entity.base

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate

/**
 * Базовый интерфейс, добавляющий поля с идентификатором сущности и датой её создания. Данные поля заполняются
 * автоматически при создании новой записи в БД.
 *
 * @property id идентификатор сущности. Необходим для корректного сохранения/обновления с использованием репозиториев.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @property updatedDate дата обновления. Записывается каждый раз при обновлении уже существующей записи в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface WithBaseData {
    @get:Id
    val id: UUID

    @get:CreatedDate
    val createdDate: Instant

    @get:LastModifiedDate
    val updatedDate: Instant?

    /**
     * Возвращает флаг того, что сущность является новой.
     */
    fun isNew(): Boolean
}