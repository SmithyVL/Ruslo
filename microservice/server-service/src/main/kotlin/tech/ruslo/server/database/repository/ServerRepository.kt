package tech.ruslo.server.database.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import tech.ruslo.server.database.entity.Server

/**
 * Репозиторий для работы с сущностью сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface ServerRepository : CoroutineCrudRepository<Server, Long>