package ru.blimfy.gateway.service.member

import ru.blimfy.gateway.dto.server.member.CurrentMemberNickDto
import ru.blimfy.gateway.dto.server.member.MemberDto
import ru.blimfy.gateway.dto.server.member.MemberNickDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberControllerService {
    /**
     * Возвращает участника сервера с новым [memberNick], которого обновляет [currentUser].
     */
    suspend fun changeMemberNick(memberNick: MemberNickDto, currentUser: User): MemberDto

    /**
     * Возвращает участника сервера с новым [memberNick], который для себя меняет [currentUser].
     */
    suspend fun changeCurrentMemberNick(memberNick: CurrentMemberNickDto, currentUser: User): MemberDto
}