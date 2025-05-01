package ru.blimfy.direct.db.entity.base

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

/**
 * Абстрактный класс с базовой информацией о сообщении.
 *
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @property updatedDate дата обновления. Записывается каждый раз при обновлении уже существующей записи в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
abstract class BaseMessage(val content: String): BaseEntity() {
    var fileUrl: String? = null

    @CreatedDate
    lateinit var createdDate: Instant

    @LastModifiedDate
    var updatedDate: Instant? = null
}