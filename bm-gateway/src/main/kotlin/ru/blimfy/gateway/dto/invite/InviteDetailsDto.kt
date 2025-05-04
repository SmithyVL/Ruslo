package ru.blimfy.gateway.dto.invite

/**
 * DTO с подробной информацией о приглашении на сервер.
 *
 * @property serverName название сервера.
 * @property countMembers количество участников на сервере.
 * @property referrerUsername имя пользователя, создавшего приглашение.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class InviteDetailsDto(val serverName: String, val countMembers: Long, val referrerUsername: String)
