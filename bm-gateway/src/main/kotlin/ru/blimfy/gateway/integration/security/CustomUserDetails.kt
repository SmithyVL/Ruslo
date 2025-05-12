package ru.blimfy.gateway.integration.security

import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.blimfy.user.db.entity.User

/**
 * Информация об авторизованном пользователе.
 *
 * @property userInfo информация пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class CustomUserDetails(val userInfo: User) : UserDetails, CredentialsContainer {
    override fun getAuthorities() = emptyList<GrantedAuthority>()

    override fun getPassword() = userInfo.passwordHash

    override fun getUsername() = userInfo.username

    override fun eraseCredentials() {
        // Затираем пароль пользователя. Метод нужно вызывать сразу после того, когда пароль больше не нужен.
        userInfo.passwordHash = ""
    }
}