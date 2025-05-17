package ru.blimfy.gateway.integration.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.blimfy.user.db.entity.User

/**
 * Информация об авторизованном пользователе.
 *
 * @property info информация пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class CustomUserDetails(val info: User) : UserDetails {
    override fun getAuthorities() = emptyList<GrantedAuthority>()
    override fun getPassword() = info.password
    override fun getUsername() = info.username
}