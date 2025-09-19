package tech.ruslo.domain.server.db.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.domain.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о сервере.
 *
 * @property ownerId идентификатор пользователя, владеющего сервером.
 * @property name название.
 * @property icon ссылка на файл аватарки.
 * @property bannerColor цвет баннера сервера.
 * @property description описание сервера.
 * @property createdDate дата создания.
 * @property roles роли сервера.
 * @property members участники сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Server(var ownerId: UUID, var name: String) : BaseEntity() {
    var icon: String? = null
    var bannerColor: String? = null
    var description: String? = null

    @CreatedDate
    lateinit var createdDate: Instant

    var roles: List<Role>? = null
    var members: List<Member>? = null
}