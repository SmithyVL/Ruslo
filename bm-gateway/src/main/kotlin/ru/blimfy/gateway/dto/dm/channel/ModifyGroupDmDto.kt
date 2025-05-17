package ru.blimfy.gateway.dto.dm.channel

import java.util.UUID

/**
 * DTO с обновлённой информацией о группе.
 *
 * @property dmChannelId идентификатор группы.
 * @property name название группы.
 * @property icon иконка группы.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyGroupDmDto(val dmChannelId: UUID, val name: String, val icon: String? = null)