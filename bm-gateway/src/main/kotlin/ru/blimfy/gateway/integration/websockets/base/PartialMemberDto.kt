package ru.blimfy.gateway.integration.websockets.base

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * DTO с частичной информацией об участнике сервера.
 *
 * @property nick ник.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PartialMemberDto(val nick: String? = null)