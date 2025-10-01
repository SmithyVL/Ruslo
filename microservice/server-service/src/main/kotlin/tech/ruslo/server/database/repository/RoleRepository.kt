package tech.ruslo.server.database.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import tech.ruslo.server.database.entity.Role

/**
 * Репозиторий для работы с сущностью роли сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface RoleRepository : CoroutineCrudRepository<Role, Long>