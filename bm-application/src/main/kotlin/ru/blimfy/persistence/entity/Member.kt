package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.MemberDto
import ru.blimfy.persistence.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию об участнике сервера.
 *
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Member(val serverId: UUID, val userId: UUID) : BaseEntity() {
    @CreatedDate
    lateinit var createdDate: Instant
}

/**
 * Возвращает DTO представление сущности участника сервера.
 */
fun Member.toDto() = MemberDto(serverId, userId, id, createdDate)

/**
 * Возвращает сущность участника сервера из DTO.
 */
fun MemberDto.toEntity() = Member(serverId, userId).apply {
    this@toEntity.id?.let { id = it }
    this@toEntity.createdDate?.let { createdDate = it }
}