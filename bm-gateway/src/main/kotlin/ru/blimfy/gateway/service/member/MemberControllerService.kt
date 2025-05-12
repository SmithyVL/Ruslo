package ru.blimfy.gateway.service.member

import ru.blimfy.gateway.dto.member.MemberDto
import ru.blimfy.gateway.dto.member.ModifyMemberDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberControllerService {
    /**
     * Возвращает обновлённого [modifyMemberDto], которого обновляет [user].
     */
    suspend fun modifyMember(modifyMemberDto: ModifyMemberDto, user: CustomUserDetails): MemberDto
}