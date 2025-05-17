package ru.blimfy.gateway.dto.server

import java.util.UUID
import ru.blimfy.server.db.entity.Server

/**
 * DTO с краткой информацией о сервере.
 *
 * @property id идентификатор.
 * @property name название.
 * @property icon ссылка на файл иконки.
 * @property bannerColor цвет баннера.
 * @property owner является ли пользователь владельцем сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ServerShortDto(
    val id: UUID,
    val name: String,
    val icon: String? = null,
    val bannerColor: String? = null,
    val owner: Boolean,
)

/**
 * Возвращает DTO краткого представления сущности сервера с указанием того, что пользователь является или не является
 * его [owner].
 */
fun Server.toShortDto(owner: Boolean) = ServerShortDto(id, name, icon, bannerColor, owner)