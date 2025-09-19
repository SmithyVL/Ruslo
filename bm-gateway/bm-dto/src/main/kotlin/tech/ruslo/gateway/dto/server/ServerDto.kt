package tech.ruslo.gateway.dto.server

import java.util.UUID
import tech.ruslo.gateway.dto.channel.ChannelDto
import tech.ruslo.gateway.dto.member.MemberDto
import tech.ruslo.gateway.dto.role.RoleDto

/**
 * DTO с информацией о сервере.
 *
 * @property id идентификатор.
 * @property name название.
 * @property ownerId идентификатор владельца сервера.
 * @property icon ссылка на файл аватарки.
 * @property bannerColor цвет баннера.
 * @property description описание сервера.
 * @property members участники сервера.
 * @property channels каналы сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ServerDto(
    val id: UUID,
    val name: String,
    val ownerId: UUID,
    val icon: String? = null,
    val bannerColor: String? = null,
    val description: String? = null,
) {
    var roles: List<RoleDto>? = null
    var members: List<MemberDto>? = null
    var channels: List<ChannelDto>? = null
}