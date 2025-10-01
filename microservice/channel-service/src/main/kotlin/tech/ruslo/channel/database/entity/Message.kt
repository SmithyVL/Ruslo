package tech.ruslo.channel.database.entity

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.common.database.BaseEntity

/**
 * Сущность с информацией о сообщениях каналов.
 *
 * @property channelId идентификатор канала.
 * @property authorId идентификатор пользователя, создавшего сообщение.
 * @property type тип сообщения.
 * @property content содержимое сообщения.
 * @property position обычно возрастающее целое число, которое представляет собой приблизительное положение сообщения.
 * @property createdDate дата создания.
 * @property updatedDate дата обновления.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class Message(val channelId: Long, val authorId: Long, val type: String) : BaseEntity() {
    lateinit var content: String
    var position: Long = 0

    @CreatedDate
    lateinit var createdDate: Instant

    @LastModifiedDate
    var updatedDate: Instant? = null
}