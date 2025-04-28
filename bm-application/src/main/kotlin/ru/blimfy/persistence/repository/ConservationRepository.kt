package ru.blimfy.persistence.repository

import java.util.UUID
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.persistence.entity.Conservation

/**
 * Репозиторий для работы с сущностью личного диалога в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface ConservationRepository : CoroutineCrudRepository<Conservation, UUID>