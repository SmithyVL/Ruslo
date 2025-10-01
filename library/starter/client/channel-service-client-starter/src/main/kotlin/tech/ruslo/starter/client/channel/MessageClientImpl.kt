package tech.ruslo.starter.client.channel

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToFlow
import tech.ruslo.channel.dto.message.MessageClient
import tech.ruslo.channel.dto.message.MessageDto

/**
 * Реализация клиентского интерфейса для работы с информацией о сообщениях из сервиса каналов.
 *
 * @property channelWebClient настроенный клиент для запросов в сервис каналов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MessageClientImpl(val channelWebClient: WebClient) : MessageClient {
    override suspend fun saveMessage(messageDto: MessageDto) = channelWebClient
        .post()
        .uri(MESSAGES_PATH_PREFIX)
        .bodyValue(messageDto)
        .retrieve()
        .awaitBody<MessageDto>()

    override fun findMessages(
        channelId: Long,
        around: Long?,
        before: Long?,
        after: Long?,
        limit: Int,
    ) = channelWebClient
        .get()
        .uri {
            it.path("$MESSAGES_PATH_PREFIX/batch")
                .queryParam("channelId", channelId)
                .queryParam("limit", limit)
                .apply {
                    around?.let { queryParam("around", around) }
                    before?.let { queryParam("before", before) }
                    after?.let { queryParam("after", after) }
                }
                .build()
        }
        .retrieve()
        .bodyToFlow<MessageDto>()

    override suspend fun deleteMessage(id: Long) = channelWebClient
        .delete()
        .uri("$MESSAGES_PATH_PREFIX/{id}", id)
        .retrieve()
        .awaitBody<Unit>()

    private companion object {
        /**
         * Префикс для запросов о сообщениях.
         */
        private const val MESSAGES_PATH_PREFIX = "/messages"
    }
}