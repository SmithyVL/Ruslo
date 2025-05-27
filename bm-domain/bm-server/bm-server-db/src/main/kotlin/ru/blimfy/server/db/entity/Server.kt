package ru.blimfy.server.db.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о сервере.
 *
 * @property ownerId идентификатор пользователя, владеющего сервером.
 * @property name название.
 * @property icon ссылка на файл аватарки.
 * @property bannerColor цвет баннера сервера.
 * @property description описание сервера.
 * @property afkChannelId идентификатор голосового канала для перемещения в него при АФК.
 * @property afkTimeout время, через которое будет совершено перемещение в АФК канал.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Server(var ownerId: UUID, var name: String) : BaseEntity() {
    var icon: String? = null
    var bannerColor: String? = null
    var description: String? = null
    //var afkChannelId: UUID? = null
    //var afkTimeout: Int? = null

    @CreatedDate
    lateinit var createdDate: Instant
}