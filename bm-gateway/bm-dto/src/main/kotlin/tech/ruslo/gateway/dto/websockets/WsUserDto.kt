package tech.ruslo.gateway.dto.websockets

import java.time.Instant
import java.util.UUID

/**
 * DTO с информацией о пользователе, расширенной информацией о сервере.
 *
 * @property id идентификатор.
 * @property username логин.
 * @property email электронная почта.
 * @property globalName отображаемое имя пользователя.
 * @property verified был ли подтвержден адрес электронной почты.
 * @property avatar ссылка на файл аватарки.
 * @property bannerColor цвет баннера пользователя.
 * @property createdDate дата создания. Записывается только один раз при создании новой записи в БД.
 * @property member информация пользователя как участника сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class WsUserDto(
    val id: UUID,
    val username: String,
    val email: String,
    val verified: Boolean = false,
    val globalName: String? = null,
    val avatar: String? = null,
    val bannerColor: String? = null,
    val createdDate: Instant,
) {
    var member: PartialMemberDto? = null
}