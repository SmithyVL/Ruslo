package tech.ruslo.gateway.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import tech.ruslo.user.dto.UserDto

/**
 * Информация об авторизованном пользователе.
 *
 * @property userDto подробная информация о пользователе.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class CustomUserDetails(val userDto: UserDto) : UserDetails {
    override fun getAuthorities() = emptyList<GrantedAuthority>()
    override fun getPassword() = userDto.passwordHash
    override fun getUsername() = userDto.username
}