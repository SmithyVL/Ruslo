package tech.ruslo.domain.server.api.dto.role

/**
 * DTO с информацией о новой роли сервера.
 *
 * @property name название.
 * @property permissions битовая маска с разрешениями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewRoleDto(val name: String, val permissions: String)