package ru.blimfy.direct.db.entity

import java.time.Instant
import java.util.UUID
import kotlin.properties.Delegates.notNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.enumeration.DirectMessageTypes.DEFAULT
import ru.blimfy.direct.db.entity.base.BaseEntity

/**
 * Сущность с информацией о сообщениях личных диалогов.
 *
 * @property conservationId идентификатор личного диалога.
 * @property authorId идентификатор автора личного сообщения
 * @property content содержимое сообщения.
 * @property type тип сообщения.
 * @property pinned является ли сообщение закреплённым.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @property updatedDate дата обновления. Записывается каждый раз при обновлении уже существующей записи в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class DirectMessage(val conservationId: UUID, val authorId: UUID, val content: String) : BaseEntity() {
    var type = DEFAULT
    var pinned by notNull<Boolean>()
    var fileUrl: String? = null
    @CreatedDate
    lateinit var createdDate: Instant
    @LastModifiedDate
    var updatedDate: Instant? = null
}