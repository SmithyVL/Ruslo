package ru.blimfy.gateway.api.server.dto

import java.util.UUID
import ru.blimfy.server.db.entity.Server

/**
 * DTO с информацией о новом сервере.
 *
 * @property name название.
 * @property icon ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewServerDto(val name: String, val icon: String? = null)

/**
 * Возвращает сущность сервера из DTO с новым сервером.
 */
fun NewServerDto.toEntity(ownerId: UUID) = Server(ownerId, name).apply { icon = this@toEntity.icon }