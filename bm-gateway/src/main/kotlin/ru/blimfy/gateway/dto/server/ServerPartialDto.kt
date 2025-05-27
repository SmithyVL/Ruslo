package ru.blimfy.gateway.dto.server

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
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
@JsonInclude(NON_NULL)
data class ServerPartialDto(
    val id: UUID,
    val name: String,
    val icon: String? = null,
    val bannerColor: String? = null,
    val owner: Boolean = false,
)

/**
 * Возвращает DTO краткого представления сущности сервера с указанием того, что пользователь является или не является
 * его [owner].
 */
fun Server.toPartialDto(owner: Boolean) = ServerPartialDto(id, name, icon, bannerColor, owner)