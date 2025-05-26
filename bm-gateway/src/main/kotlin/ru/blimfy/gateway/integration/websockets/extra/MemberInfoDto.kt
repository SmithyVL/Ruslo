package ru.blimfy.gateway.integration.websockets.extra

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.util.UUID
import ru.blimfy.gateway.integration.websockets.base.PartialMemberDto

/**
 * DTO с дополнительной информацией об участнике сервера.
 *
 * @property serverId идентификатор сервера.
 * @property member информация об участнике сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(NON_NULL)
data class MemberInfoDto(val serverId: UUID, val member: PartialMemberDto)