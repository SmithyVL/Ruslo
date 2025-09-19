package tech.ruslo.gateway.mapper

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import tech.ruslo.domain.channel.usecase.channel.ChannelService
import tech.ruslo.domain.server.db.entity.Server
import tech.ruslo.gateway.dto.server.PartialServerDto
import tech.ruslo.gateway.dto.server.ServerDto

/**
 * Маппер для превращения сервера в DTO и обратно.
 *
 * @property channelService сервис для работы с каналами.
 * @property memberMapper маппер для работы с участниками.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class ServerMapper(private val channelService: ChannelService, private val memberMapper: MemberMapper) {
    /**
     * Возвращает DTO представление из информации о [server] вместе с его ролями.
     */
    suspend fun toDto(server: Server) =
        server.toDto().apply {
            server.roles?.let { roles ->
                this.roles = roles.map { role -> role.toDto() }
            }
            server.members?.let { members ->
                this.members = members.map { member -> memberMapper.toDto(member) }
            }
            channels = channelService.findServerChannels(id)
                .map { channel -> channel.toBasicDto() }
                .toList()
        }
}

/**
 * Возвращает частичное DTO представление сервера.
 */
fun Server.toPartialDto() = PartialServerDto(id, name, icon, bannerColor, description)

/**
 * Возвращает DTO представление сервера.
 */
fun Server.toDto() = ServerDto(id, name, ownerId, icon, bannerColor, description)