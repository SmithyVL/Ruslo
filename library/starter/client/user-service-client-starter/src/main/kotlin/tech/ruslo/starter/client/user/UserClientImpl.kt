package tech.ruslo.starter.client.user

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import tech.ruslo.user.dto.UserClient
import tech.ruslo.user.dto.UserDto

/**
 * Реализация клиентского интерфейса для работы с информацией о пользователях из сервиса пользователей.
 *
 * @property userWebClient настроенный клиент для запросов в сервис пользователей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserClientImpl(val userWebClient: WebClient) : UserClient {
    override suspend fun saveUser(userDto: UserDto) = userWebClient
        .post()
        .uri(USER_PATH_PREFIX)
        .bodyValue(userDto)
        .retrieve()
        .awaitBody<UserDto>()

    override suspend fun getUser(id: Long) = userWebClient
        .get()
        .uri("$USER_PATH_PREFIX/{id}", id)
        .retrieve()
        .awaitBody<UserDto>()

    override suspend fun getUser(username: String) = userWebClient
        .get()
        .uri {
            it.path(USER_PATH_PREFIX)
                .queryParam("username", username)
                .build()
        }
        .retrieve()
        .awaitBody<UserDto>()

    private companion object {
        /**
         * Префикс для запросов о каналах.
         */
        private const val USER_PATH_PREFIX = "/users"
    }
}