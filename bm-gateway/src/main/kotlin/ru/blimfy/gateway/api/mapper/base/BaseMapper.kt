package ru.blimfy.gateway.api.mapper.base

/**
 * Интерфейс для превращения сущности в DTO.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
abstract class BaseMapper<ENTITY, DTO> {
    /**
     * Возвращает DTO представление с из [entity] со всей связанной информацией.
     */
    abstract suspend fun toDtoWithRelations(entity: ENTITY): DTO

    /**
     * Возвращает DTO представление из сущности.
     */
    protected abstract suspend fun ENTITY.toDto(): DTO
}