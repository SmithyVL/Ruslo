package tech.ruslo.gateway.dto.ban

/**
 * DTO с информацией о новом бане.
 *
 * @property reason причина.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewBanDto(val reason: String? = null)