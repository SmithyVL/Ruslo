package ru.blimfy.server.db.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о сервере.
 *
 * @property ownerUserId идентификатор пользователя, владеющего сервером.
 * @property name название.
 * @property avatarUrl ссылка на файл аватарки.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Server(val ownerUserId: UUID, val name: String) : BaseEntity() {
    var avatarUrl: String? = null

    @CreatedDate
    lateinit var createdDate: Instant
}